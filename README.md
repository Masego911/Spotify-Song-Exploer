# Spotify Recommendation Dashboard

## Overview
This project is a graph-based music recommendation system with an interactive analytics dashboard.

It processes real-world streaming data, builds a similarity graph between songs, and generates recommendations based on graph relationships. The results are visualised through a desktop dashboard with charts and insights.

---

## Features

### Search & Discovery
- Search songs or artists
- View matching results instantly
- Select any song as a starting point

### Recommendation Engine
- Graph-based similarity model
- k-nearest neighbour (k-NN) style edge construction
- Recommendations generated from connected nodes

### Analytics Dashboard
- Recommendation comparison (bar chart)
- Genre distribution (pie chart)
- Top artists (bar chart)
- Song-level insights (streams, artist, genre)

### Visual Interface
- Interactive dashboard built with Java Swing
- Structured layout with multiple panels
- Real-time updates when selecting songs

---

## How It Works

### 1. Data Pipeline (ETL)
- CSV dataset is loaded into SQLite
- Data is cleaned and transformed
- Stored in relational format

### 2. Graph Construction
- Each song = a vertex
- Similar songs are connected via edges
- Similarity based on numeric features (e.g. streams)

### 3. Recommendation Logic
- For a selected song:
  - Traverse neighbours
  - Rank similar songs
  - Return top-k recommendations

### 4. Visualisation
- Charts generated using JFreeChart
- Dashboard updates dynamically based on user interaction

---

## Tech Stack

- **Java** — core application logic
- **Swing** — dashboard UI
- **SQLite** — database storage
- **JFreeChart** — data visualisation
- **Graph Algorithms** — recommendation engine

---

## Project Structure
spotify-recommendation-dashboard/
│
├── src/
│ ├── Main.java
│ ├── DashboardUI.java
│ ├── ChartBuilder.java
│ ├── Graph.java
│ ├── Vertex.java
│ ├── Recommender.java
│ └── ...
│
├── assets/
│ └── song_placeholder.png
│
├── data/
│ └── Most Streamed Spotify Songs 2024.csv
│
├── lib/
│ └── jfreechart-1.5.3.jar
│
├── README.md
└── .gitignore
