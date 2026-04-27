import org.jfree.chart.ChartFactory; // creates charts
import org.jfree.chart.ChartPanel;   // embeds chart in Swing
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * ChartBuilder
 *
 * PURPOSE:
 * Builds all charts used in the dashboard
 */
public class ChartBuilder {

    // -----------------------------------------
    // 1. RECOMMENDATION BAR CHART
    // -----------------------------------------
    public static JPanel buildRecommendationChart(List<Vertex> recs) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Vertex v : recs) {

            dataset.addValue(
                    v.getSong().getStreams(),
                    "Streams",
                    v.getSong().getTitle()
            );
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Top Recommendations",
                "Song",
                "Streams",
                dataset
        );

        styleBarChart(chart);

        return new ChartPanel(chart);
    }

    // -----------------------------------------
    // 2. GENRE PIE CHART
    // -----------------------------------------
    public static JPanel buildGenreChart(List<Vertex> vertices) {

        DefaultPieDataset dataset = new DefaultPieDataset();

        Map<String, Integer> genreCount = new HashMap<>();

        for (Vertex v : vertices) {

            String genre = v.getSong().getGenre();

            genreCount.put(genre, genreCount.getOrDefault(genre, 0) + 1);
        }

        for (String genre : genreCount.keySet()) {
            dataset.setValue(genre, genreCount.get(genre));
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Genre Distribution",
                dataset,
                true,
                true,
                false
        );

        chart.setBackgroundPaint(new Color(30, 30, 45));

        return new ChartPanel(chart);
    }

    // -----------------------------------------
    // 3. ARTIST BAR CHART
    // -----------------------------------------
    public static JPanel buildArtistChart(List<Vertex> vertices) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String, Integer> artistCount = new HashMap<>();

        for (Vertex v : vertices) {

            String artist = v.getSong().getArtist();

            artistCount.put(artist, artistCount.getOrDefault(artist, 0) + 1);
        }

        for (String artist : artistCount.keySet()) {

            dataset.addValue(
                    artistCount.get(artist),
                    "Songs",
                    artist
            );
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Top Artists",
                "Artist",
                "Song Count",
                dataset
        );

        styleBarChart(chart);

        return new ChartPanel(chart);
    }

    // -----------------------------------------
    // SHARED STYLE
    // -----------------------------------------
    private static void styleBarChart(JFreeChart chart) {

        chart.setBackgroundPaint(new Color(30, 30, 45));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(20, 20, 30));
    }
}