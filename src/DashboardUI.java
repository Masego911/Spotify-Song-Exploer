import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardUI extends JFrame {

    // DESIGN SYSTEM
    private final Color BG = new Color(15, 15, 25);
    private final Color CARD = new Color(30, 30, 45);
    private final Color ACCENT = new Color(138, 43, 226);

    private final Color TEXT_PRIMARY = Color.WHITE;

    private Graph graph;
    private Recommender recommender;

    private JTextField searchField;
    private DefaultListModel<String> resultsModel;
    private DefaultListModel<String> recModel;
    private JTextArea metricsArea;

    private JPanel chartContainer;
    private JPanel genreChartContainer;
    private JPanel artistChartContainer;

    private JLabel albumImage;

    private List<Vertex> currentMatches;

    public DashboardUI(Graph graph) {

        this.graph = graph;
        this.recommender = new Recommender(graph);

        setTitle("Music Dashboard");
        setSize(1200, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(BG);

        // -----------------------------
        // TOP
        // -----------------------------
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(25, 25, 40));

        searchField = new JTextField(30);
        JButton searchButton = new JButton("Search");

        topPanel.add(searchField);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        // -----------------------------
        // COMPONENTS
        // -----------------------------
        resultsModel = new DefaultListModel<>();
        JList<String> resultsList = new JList<>(resultsModel);

        recModel = new DefaultListModel<>();
        JList<String> recList = new JList<>(recModel);

        metricsArea = new JTextArea();
        metricsArea.setEditable(false);

        chartContainer = new JPanel(new BorderLayout());
        genreChartContainer = new JPanel(new BorderLayout());
        artistChartContainer = new JPanel(new BorderLayout());

        // ALBUM IMAGE
        albumImage = new JLabel("Album Image", JLabel.CENTER);

        // -----------------------------
        // CENTER LAYOUT
        // -----------------------------
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // ROW 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0.3;
        center.add(createCard("Search Results", new JScrollPane(resultsList)), gbc);

        gbc.gridx = 1;
        center.add(createCard("Recommendations", new JScrollPane(recList)), gbc);

        // ROW 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        center.add(createCard("Song Insights", new JScrollPane(metricsArea)), gbc);

        gbc.gridx = 1;
        center.add(createCard("Album Art", albumImage), gbc);

        // ROW 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        center.add(createCard("Recommendation Analytics", chartContainer), gbc);

        // ROW 4
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        center.add(createCard("Genre Distribution", genreChartContainer), gbc);

        gbc.gridx = 1;
        center.add(createCard("Top Artists", artistChartContainer), gbc);

        add(center, BorderLayout.CENTER);

        // -----------------------------
        // ACTIONS
        // -----------------------------
        searchButton.addActionListener(e -> {

            String query = searchField.getText();

            resultsModel.clear();
            currentMatches = Main.findVertices(graph, query);

            for (Vertex v : currentMatches) {

                resultsModel.addElement(
                        v.getSong().getArtist() + " - " +
                                v.getSong().getTitle()
                );
            }
        });

        resultsList.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting() && resultsList.getSelectedIndex() != -1) {

                Vertex selected = currentMatches.get(resultsList.getSelectedIndex());

                List<Vertex> recs = recommender.getTopKRecommendations(selected, 5);

                // RECOMMENDATIONS
                recModel.clear();

                for (Vertex v : recs) {
                    recModel.addElement(v.getSong().getArtist() + " - " + v.getSong().getTitle());
                }

                // METRICS
                metricsArea.setText(
                        "Title: " + selected.getSong().getTitle() + "\n\n" +
                                "Artist: " + selected.getSong().getArtist() + "\n\n" +
                                "Streams: " + selected.getSong().getStreams() + "\n\n" +
                                "Genre: " + selected.getSong().getGenre()
                );

                // CHARTS
                updateCharts(recs);
            }
        });
    }

    private void updateCharts(List<Vertex> recs) {

        // Recommendation chart
        chartContainer.removeAll();
        chartContainer.add(ChartBuilder.buildRecommendationChart(recs));
        chartContainer.revalidate();
        chartContainer.repaint();

        // Genre chart
        genreChartContainer.removeAll();
        genreChartContainer.add(ChartBuilder.buildGenreChart(graph.getVertices()));
        genreChartContainer.revalidate();
        genreChartContainer.repaint();

        // Artist chart
        artistChartContainer.removeAll();
        artistChartContainer.add(ChartBuilder.buildArtistChart(graph.getVertices()));
        artistChartContainer.revalidate();
        artistChartContainer.repaint();
    }

    private JPanel createCard(String title, Component content) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createTitledBorder(title));

        panel.add(content, BorderLayout.CENTER);

        return panel;
    }
}