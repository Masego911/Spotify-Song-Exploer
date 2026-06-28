# Spotify Song Explorer

A graph-based music analytics and recommendation system built using Java, SQLite, and Spotify streaming data.

This project explores how graph theory, recommendation algorithms, ETL pipelines, and database systems can be used to analyse relationships between songs and generate music recommendations based on similarity metrics.

The system processes Spotify streaming datasets, transforms songs into graph structures, computes weighted relationships between tracks, and applies traversal and recommendation algorithms to explore song connectivity and recommendation paths.

---

# Project Background

This project was built as part of an ongoing learning journey into:

* software engineering
* graph theory
* recommendation systems
* database systems
* data processing pipelines
* algorithm design
* music analytics

The project originally began as an exploration of how streaming platforms organise and relate songs through:

* similarity analysis
* graph relationships
* recommendation structures
* metadata processing

The system evolved into a larger experimental analytics platform combining:

* ETL processing
* graph modelling
* SQLite persistence
* traversal algorithms
* recommendation logic

using real Spotify streaming datasets.

---

# Learning and Development Context

This repository was developed while actively learning:

* Java
* graph data structures
* software architecture
* database integration
* ETL pipelines
* recommendation systems
* algorithmic thinking

As a result, parts of the codebase reflect:

* experimentation
* iterative development
* architectural exploration
* evolving coding practices
* rapid prototyping

The repository intentionally documents both:

* the capabilities of the project
  and
* the progression of my technical development over time.

Future iterations aim to improve:

* modularity
* maintainability
* separation of concerns
* database design
* scalability
* recommendation quality
* algorithm efficiency
* code organisation

---

# Technologies Used

| Technology                | Purpose                                      |
| ------------------------- | -------------------------------------------- |
| Java                      | Core application development                 |
| SQLite                    | Persistent relational database storage       |
| JDBC                      | Database connectivity                        |
| CSV Processing            | Spotify dataset ingestion                    |
| Graph Structures          | Song relationship modelling                  |
| ETL Pipeline Architecture | Data extraction, transformation, and loading |
| Git & GitHub              | Version control                              |
| IntelliJ IDEA             | Development environment                      |

## Build and run

Requirements: JDK 11 or newer and Maven 3.8 or newer.

```text
mvn test
mvn package
```

Run `Main` from IntelliJ. Pass `--import` to replace the database contents from
the bundled CSV; when the database is empty, the application imports it automatically.

---

# Dataset

The system uses Spotify streaming datasets containing:

* song metadata
* artists
* streaming statistics
* popularity metrics
* audio characteristics

The project currently processes:

```text
Most Streamed Spotify Songs 2024.csv
```

through a custom ETL pipeline before loading structured data into SQLite and graph structures.

---

# Core System Architecture

The project follows a pipeline-based architecture:

```text
CSV Dataset
    ↓
Extractor
    ↓
Transformer
    ↓
Loader
    ↓
SQLite Database
    ↓
Graph Construction
    ↓
Recommendation Engine
```

---

# Project Components

| Component         | Responsibility                         |
| ----------------- | -------------------------------------- |
| Extractor.java    | Reads raw Spotify CSV datasets         |
| Transformer.java  | Cleans and transforms raw song data    |
| Loader.java       | Loads processed data into storage      |
| ETLPipeline.java  | Coordinates ETL workflow               |
| DBConnection.java | Manages SQLite connections             |
| DBSetup.java      | Creates and configures database tables |
| Graph.java        | Stores graph structure                 |
| Vertex.java       | Represents songs/nodes                 |
| Edge.java         | Represents weighted song relationships |
| EdgeBuilder.java  | Computes similarity relationships      |
| BFS.java          | Breadth-First Search traversal         |
| DFS.java          | Depth-First Search traversal           |
| Recommender.java  | Generates song recommendations         |
| GraphService.java | Graph management operations            |
| GraphLoader.java  | Loads graph data from database         |
| Song.java         | Song data model                        |
| Main.java         | Application entry point                |

---

# Algorithms and Computational Methods Used

## Graph Theory Algorithms

### Graph Construction

Songs are represented as vertices within a graph structure.

Relationships between songs are represented as weighted edges based on similarity metrics.

This enables:

* song relationship modelling
* recommendation generation
* similarity traversal
* graph-based analytics

---

### Breadth-First Search (BFS)

The project implements Breadth-First Search traversal for:

* graph exploration
* neighbourhood discovery
* recommendation path analysis
* connectivity traversal

---

### Depth-First Search (DFS)

Depth-First Search is used for:

* deep graph traversal
* connected exploration
* recursive path analysis
* structural graph navigation

---

### Weighted Edge Similarity

The system computes weighted relationships between songs based on similarity features extracted from metadata and streaming attributes.

This supports:

* recommendation ranking
* song clustering
* relationship scoring
* graph connectivity analysis

---

## Recommendation Algorithms

### Graph-Based Recommendation System

The recommendation engine uses graph traversal and weighted similarity relationships to identify related songs.

Recommendations are generated using:

* edge weights
* traversal depth
* graph neighbourhood relationships
* similarity scoring

---

### Similarity Scoring

The system calculates similarity scores between songs using metadata-driven comparison logic.

This helps identify:

* related tracks
* structurally similar songs
* recommendation candidates

---

# Data Structures Used

| Data Structure     | Purpose                                |
| ------------------ | -------------------------------------- |
| Graph              | Core song relationship modelling       |
| Vertex             | Represents individual songs            |
| Edge               | Represents weighted song relationships |
| ArrayList          | Dynamic song and edge storage          |
| Queue / LinkedList | BFS traversal                          |
| Stack / Recursion  | DFS traversal                          |
| HashMap            | Fast lookup and graph mapping          |
| SQLite Tables      | Persistent structured storage          |

---

# Database Integration

The project integrates SQLite using JDBC for:

* persistent storage
* structured querying
* graph loading
* song retrieval
* recommendation processing

The database layer currently includes:

* database setup
* connection management
* graph loading support
* persistent song storage

Database file:

```text
spotify.db
```

---

# ETL Pipeline

The project includes a custom ETL pipeline implementing:

## Extract

Reads raw Spotify CSV data.

## Transform

Cleans, structures, and processes song information.

## Load

Loads processed records into SQLite and graph structures.

This pipeline architecture simulates real-world data engineering workflows used in analytics systems.

---

# Software Engineering Concepts Demonstrated

This project demonstrates:

* Object-Oriented Programming (OOP)
* Graph modelling
* Graph traversal algorithms
* ETL pipeline architecture
* Recommendation systems
* SQLite integration
* JDBC database connectivity
* Data transformation workflows
* Algorithmic design
* Data structure implementation
* Modular system organisation

---

# Planned Improvements

Future development will focus on:

* improved recommendation algorithms
* collaborative filtering
* content-based recommendation models
* graph visualisation
* modular architecture
* improved separation of concerns
* scalable graph processing
* performance optimisation
* UI improvements
* API integration
* machine learning experimentation

---

# Planned Features

## Recommendation Enhancements

* weighted recommendation ranking
* multi-hop recommendation traversal
* genre-aware recommendations
* playlist similarity analysis

## Analytics Features

* artist relationship graphs
* genre clustering
* streaming trend analysis
* recommendation path visualisation

## Engineering Improvements

* layered architecture
* REST API integration
* scalable graph persistence
* improved indexing systems
* caching strategies

---

# Repository Structure

```text
spotify-song-explorer/
│
├── src/
│   ├── BFS.java
│   ├── DFS.java
│   ├── Edge.java
│   ├── EdgeBuilder.java
│   ├── ETLPipeline.java
│   ├── Extractor.java
│   ├── Graph.java
│   ├── GraphLoader.java
│   ├── GraphService.java
│   ├── Loader.java
│   ├── Main.java
│   ├── Recommender.java
│   ├── Song.java
│   ├── Transformer.java
│   └── Vertex.java
│
├── spotify.db
├── Most Streamed Spotify Songs 2024.csv
├── README.md
└── .gitignore
```

---

# Why This Project Matters

Modern music platforms are fundamentally driven by:

* graph relationships
* recommendation systems
* similarity modelling
* data pipelines
* large-scale analytics systems

This project explores how:

* graph theory
* traversal algorithms
* ETL pipelines
* recommendation systems
* database engineering

can be combined to build experimental music intelligence systems.

---

# Long-Term Vision

The long-term goal is to evolve this repository into a more advanced music analytics and recommendation platform incorporating:

* machine learning recommendation systems
* graph analytics
* audio feature analysis
* scalable graph databases
* recommendation optimisation
* distributed data processing
* interactive graph visualisation

---

# Important Notes

This project is experimental and educational in nature.

It was developed as part of an ongoing software engineering and computational analytics learning process and should be viewed as both:

* a functional recommendation exploration system
  and
* a technical growth project documenting evolving engineering practices.

---

# Author

Masego Madisha

BCom Computer Science and Information Systems student
