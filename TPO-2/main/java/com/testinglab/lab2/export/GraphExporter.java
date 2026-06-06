package com.testinglab.lab2.export;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.imageio.ImageIO;

public final class GraphExporter {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 720;
    private static final int LEFT = 76;
    private static final int RIGHT = 34;
    private static final int TOP = 58;
    private static final int BOTTOM = 70;

    public void export(Path csvPath, Path pngPath, String title) throws IOException {
        List<Point> points = readPoints(csvPath);
        Files.createDirectories(pngPath.toAbsolutePath().getParent());

        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        try {
            render(graphics, points, title);
        } finally {
            graphics.dispose();
        }

        ImageIO.write(image, "png", pngPath.toFile());
    }

    private static List<Point> readPoints(Path csvPath) throws IOException {
        List<String> lines = Files.readAllLines(csvPath, StandardCharsets.UTF_8);
        List<Point> points = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",", -1);
            if (parts.length != 2) {
                continue;
            }
            double x = parse(parts[0]);
            double y = parse(parts[1]);
            points.add(new Point(x, y));
        }
        return points;
    }

    private static void render(Graphics2D graphics, List<Point> points, String title) {
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        Bounds bounds = Bounds.from(points);
        drawFrame(graphics, title);
        drawAxes(graphics, bounds);
        drawSeries(graphics, points, bounds);
    }

    private static void drawFrame(Graphics2D graphics, String title) {
        graphics.setColor(new Color(32, 36, 44));
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        graphics.drawString(title, LEFT, 36);

        graphics.setColor(new Color(216, 222, 232));
        graphics.setStroke(new BasicStroke(1.2f));
        graphics.drawRect(LEFT, TOP, WIDTH - LEFT - RIGHT, HEIGHT - TOP - BOTTOM);
    }

    private static void drawAxes(Graphics2D graphics, Bounds bounds) {
        graphics.setStroke(new BasicStroke(1.0f));
        graphics.setColor(new Color(134, 144, 160));

        if (bounds.minX <= 0.0d && bounds.maxX >= 0.0d) {
            int zeroX = mapX(0.0d, bounds);
            graphics.drawLine(zeroX, TOP, zeroX, HEIGHT - BOTTOM);
        }
        if (bounds.minY <= 0.0d && bounds.maxY >= 0.0d) {
            int zeroY = mapY(0.0d, bounds);
            graphics.drawLine(LEFT, zeroY, WIDTH - RIGHT, zeroY);
        }
    }

    private static void drawSeries(Graphics2D graphics, List<Point> points, Bounds bounds) {
        graphics.setColor(new Color(30, 109, 184));
        graphics.setStroke(new BasicStroke(2.0f));

        Path2D path = new Path2D.Double();
        boolean drawing = false;
        for (Point point : points) {
            if (!point.isFinite() || point.y < bounds.minY || point.y > bounds.maxY) {
                if (drawing) {
                    graphics.draw(path);
                    path.reset();
                    drawing = false;
                }
                continue;
            }

            int pixelX = mapX(point.x, bounds);
            int pixelY = mapY(point.y, bounds);
            if (!drawing) {
                path.moveTo(pixelX, pixelY);
                drawing = true;
            } else {
                path.lineTo(pixelX, pixelY);
            }
        }

        if (drawing) {
            graphics.draw(path);
        }
    }

    private static int mapX(double x, Bounds bounds) {
        double scale = (WIDTH - LEFT - RIGHT) / (bounds.maxX - bounds.minX);
        return (int) Math.round(LEFT + (x - bounds.minX) * scale);
    }

    private static int mapY(double y, Bounds bounds) {
        double scale = (HEIGHT - TOP - BOTTOM) / (bounds.maxY - bounds.minY);
        return (int) Math.round(HEIGHT - BOTTOM - (y - bounds.minY) * scale);
    }

    private static double parse(String raw) {
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException exception) {
            return Double.NaN;
        }
    }

    private record Point(double x, double y) {

        boolean isFinite() {
            return Double.isFinite(x) && Double.isFinite(y);
        }
    }

    private record Bounds(double minX, double maxX, double minY, double maxY) {

        static Bounds from(List<Point> points) {
            List<Point> finite = points.stream()
                    .filter(Point::isFinite)
                    .toList();
            if (finite.isEmpty()) {
                return new Bounds(-1.0d, 1.0d, -1.0d, 1.0d);
            }

            double minX = finite.stream().map(Point::x).min(Double::compare).orElse(-1.0d);
            double maxX = finite.stream().map(Point::x).max(Double::compare).orElse(1.0d);

            List<Double> yValues = finite.stream()
                    .map(Point::y)
                    .sorted(Comparator.naturalOrder())
                    .toList();
            double minY = percentile(yValues, 0.05d);
            double maxY = percentile(yValues, 0.95d);

            if (Math.abs(maxX - minX) <= 1e-12) {
                minX -= 1.0d;
                maxX += 1.0d;
            }
            if (Math.abs(maxY - minY) <= 1e-12) {
                minY -= 1.0d;
                maxY += 1.0d;
            }

            double yPadding = (maxY - minY) * 0.08d;
            return new Bounds(minX, maxX, minY - yPadding, maxY + yPadding);
        }

        private static double percentile(List<Double> values, double ratio) {
            int index = (int) Math.round((values.size() - 1) * ratio);
            return values.get(Math.max(0, Math.min(values.size() - 1, index)));
        }
    }
}
