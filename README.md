# IdeaProjects

A collection of small Java projects and experiments created while learning and practicing Java with IntelliJ IDEA.

## Projects

| Project | Description |
| --- | --- |
| [`Intro/`](Intro/) | Java learning exercises and examples, including introductory programs, methods, recursion, and a digital clock. |
| [`JavaCountdownTimer/`](JavaCountdownTimer/) | A Java Swing countdown-timer interface with hour, minute, and second inputs and Start, Pause, and Reset buttons. The controls are currently a UI prototype; timer behavior has not been implemented yet. |
| [`IdeaHome/`](IdeaHome/) | IntelliJ IDEA project configuration. |

Each project is kept in its own directory and has a Maven `pom.xml` where applicable.

## Requirements

- JDK 25
- Apache Maven
- IntelliJ IDEA (recommended)

The Maven projects set their compiler source and target to Java 25, so use a compatible JDK.

## Build

From the repository root, build a project by running Maven in its directory. For example:

```bash
cd Intro
mvn package
```

Or build the countdown timer:

```bash
cd JavaCountdownTimer
mvn package
```

To explore or run a project in IntelliJ IDEA, open its directory (or open the repository and import the Maven project), then run the desired Java class from the IDE.

## Repository layout

```text
IdeaProjects/
├── IdeaHome/
├── Intro/
│   ├── pom.xml
│   └── src/main/java/
└── JavaCountdownTimer/
    ├── pom.xml
    └── src/main/java/
```

This repository is a personal learning playground; projects and examples may evolve over time.
