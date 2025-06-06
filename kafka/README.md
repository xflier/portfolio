# Kafka Portfolio

This project demonstrates the setup of [Apache Kafka](https://kafka.apache.org/) in combined mode with 3 nodes

## Features

- A Kafka cluster with 3 nodes - broker and controller are in same node.  
- replication factor is 3
- optionally a UI for Kafka cluster

## Prerequisites

- Java 8+ or compatible runtime
- Docker (for running Kafka locally)

## Getting Started

1. **Clone the repository:**
    ```bash
    git clone https://github.com/yourusername/kafka-portfolio.git
    cd kafka-portfolio
    ```

2. **Start Kafka using Docker:**
    ```bash
    docker-compose up -d
    ```
