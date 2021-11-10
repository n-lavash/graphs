import java.util.*;

public class Console {
    public static void start() {
        createNotWeightedNotOrientationGraph();
        createWeightedNotOrientationGraph();
        createNotWeightedOrientationGraph();
        createWeightedOrientationGraph();

        testConsole();
    }

    static void createNotWeightedNotOrientationGraph() {
        Graph<String> graph = new Graph<>(false, false);

        try {
            graph.addVertex("Маркс");  // изолированная вершина

            graph.addEdge("Астрахань", "Астрахань"); // петля
            graph.addEdge("Астрахань", "Киров");
            graph.addEdge("Киров", "Саратов");
            graph.addEdge("Саратов", "Судак");
            graph.addEdge("Судак", "Новый Свет");
            graph.addEdge("Самара", "Красноярск");
            graph.addEdge("Самара", "Саратов");
            graph.addEdge("Краснодар", "Саратов");
            consoleOutput(graph); // выводим граф в консоль
            saveFile(graph, "NotWeightedNotOrientationGraph.bin");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void createWeightedNotOrientationGraph() {
        Graph<String> graph = new Graph<>(true, false);

        try {
            graph.addEdge("Астрахань", "Саратов", 7);
            graph.addEdge("Астрахань", "Судак", 5);
            graph.addEdge("Саратов", "Самара", 8);
            graph.addEdge("Саратов", "Казань", 7);
            graph.addEdge("Саратов", "Судак", 9);
            graph.addEdge("Самара", "Казань", 5);
            graph.addEdge("Судак", "Казань", 15);
            graph.addEdge("Судак", "Новый Свет", 6);
            graph.addEdge("Новый Свет", "Казань", 8);
            graph.addEdge("Новый Свет", "Краснодар", 11);
            graph.addEdge("Краснодар", "Казань", 9);


            consoleOutput(graph);
            saveFile(graph, "WeightedNotOrientationGraph.bin");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void createNotWeightedOrientationGraph() {
        Graph<String> graph = new Graph<>(false, true);

        try {
            graph.addVertex("Маркс");  // изолированная вершина

            graph.addEdge("Астрахань", "Астрахань"); // петля
            graph.addEdge("Астрахань", "Киров");
            graph.addEdge("Киров", "Саратов");
            graph.addEdge("Саратов", "Судак");
            graph.addEdge("Судак", "Новый Свет");
            graph.addEdge("Самара", "Красноярск");
            graph.addEdge("Самара", "Саратов");
            graph.addEdge("Краснодар", "Саратов");
            consoleOutput(graph);
            saveFile(graph, "NotWeightedOrientationGraph.bin");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void createWeightedOrientationGraph() {
        Graph<String> graph = new Graph<>(true, true);

        try {
            graph.addVertex("Маркс");  // изолированная вершина

            graph.addEdge("Астрахань", "Астрахань", 1); // петля
            graph.addEdge("Астрахань", "Киров", 18);
            graph.addEdge("Киров", "Саратов", 10);
            graph.addEdge("Саратов", "Судак", 14);
            graph.addEdge("Судак", "Новый Свет", 2);
            graph.addEdge("Самара", "Красноярск", 32);
            graph.addEdge("Самара", "Саратов", 4);
            graph.addEdge("Краснодар", "Саратов", 17);
            consoleOutput(graph);
            saveFile(graph, "WeightedOrientationGraph.bin");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void testConsole() {
        Scanner scanner = new Scanner(System.in);
        Graph<String> graph;
        String commandWord;

        while (true) {
            System.out.println("Прочитать граф из файла? (Да/Нет)");
            commandWord = scanner.nextLine();
            if(commandWord.equals("Да") || commandWord.equals("да")) {
                System.out.println("Введите название файла:");
                String fileName = scanner.nextLine();
                try {
                    graph = AdditionalClass.readFromFile(fileName);
                    System.out.println("Хотите изменить выгруженный граф? (Да/Нет)");
                    String answer = scanner.nextLine();
                    if(answer.equals("Да") || answer.equals("да")) {
                        break;
                    } else {
                        consoleOutput(graph);
                        return;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (commandWord.equals("Нет") || commandWord.equals("нет")) {
                int answered1;
                while (true) {
                    System.out.println("Граф ориентированный?\n" +
                            "\tОриентированный - 1\n" +
                            "\tНеориентированный - 2");
                    answered1 = scanner.nextInt();
                    if (answered1 != 1 && answered1 !=2){
                        System.out.println("Введено неверное значение. Попробуйте снова");
                    } else {
                        break;
                    }
                }

                int answered2;
                while (true) {
                    System.out.println("Граф взвешенный?\n" +
                            "\tВзвешенный - 1\n" +
                            "\tНевзвешенный - 2\n");
                    answered2 = scanner.nextInt();
                    if (answered2 != 1 && answered2 != 2){
                        System.out.println("Введено неверное значение. Попробуйте снова");
                    } else {
                        break;
                    }
                }
                graph = new Graph<>(answered1 == 1, answered2 == 1);
                break;
            } else {
                System.out.println("Введено неверное значение. Попробуйте снова");
            }
        }

        String commandNumber;
        scanner.nextLine();

        System.out.println("Команды:\n\t" +
                "1 - Показать граф\n\t" +
                "2 - Добавить ребро\n\t" +
                "3 - Удалить ребро\n\t" +
                "4 - Добавить вершину\n\t" +
                "5 - Удалить вершину\n\t" +
                "6 - Перейти к домашнему заданию\n\t" +
                "7 - Сохранить в файл\n\t" +
                "0 - Закончить тестирование");
        while (true) {
            System.out.println("Введите название команды");
            commandNumber = scanner.nextLine();
            switch (commandNumber) {
                case "1":
                    consoleOutput(graph);
                    break;
                case "2":
                    addEdge(graph);
                    break;
                case "3":
                    deleteEdge(graph);
                    break;
                case "4":
                    addVertex(graph);
                    break;
                case "5":
                    deleteVertex(graph);
                    break;
                case "6":
                    homeworkConsole(graph);
                    break;
                case "7":
                    saveFile(graph, null);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Введено неверное значение. Попробуйте снова\n" + "Команды:\n\t" +
                            "1 - Показать граф\n\t" +
                            "2 - Добавить ребро\n\t" +
                            "3 - Удалить ребро\n\t" +
                            "4 - Добавить вершину\n\t" +
                            "5 - Удалить вершину\n\t" +
                            "6 - Перейти к домашнему заданию\n\t" +
                            "7 - Сохранить в файл\n\t" +
                            "0 - Закончить тестирование");
            }
        }
    }

    static void homeworkConsole(Graph<String> graph) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Команды:\n" +
                "1 - Список смежности Ia. Первое задание (Сдано)\n" +
                "2 - Список смежности Ia. Второе задание (Сдано)\n" +
                "3 - Список смежности Iб: несколько графов\n" +
                "4 - Обходы графа II\n" +
                "5 - Обходы графа II\n" +
                "6 - Каркас III\n" +
                "7 - Веса IV а\n" +
                "8 - Веса IV b\n" +
                "9 - Веса IV с\n" +
                "0 - Закончить просмотр");
        while (true) {
            System.out.println("Введите название команды:");
            String answer = scanner.nextLine();
            switch (answer) {
                case "1" :
                    Homework.Ia1(graph);
                    break;
                case "2" :
                    Homework.Ia2(graph);
                    break;
                case "3" :
                    Homework.Ib(graph);
                    break;
                case "4" :
                    Homework.II1(graph);
                    break;
                case "5" :
                    Homework.II2(graph);
                    break;
                case "6" :
                    Homework.III(graph);
                    break;
                case "7" :
                    Homework.IVa(graph);
                    break;
                case "8" :
                    Homework.IVb(graph);
                    break;
                case "9" :
                    Homework.IVc(graph);
                    break;
                case "0" :
                    return;
                default:
                    System.out.println("Введено неверное значение. Попробуйте снова\n" +
                            "Команды:\n" +
                            "1 - Список смежности Ia. Первое задание\n" +
                            "2 - Список смежности Ia. Второе задание\n" +
                            "3 - Список смежности Iб: несколько графов\n" +
                            "4 - Обходы графа II\n" +
                            "5 - Обходы графа II\n" +
                            "6 - Каркас III\n" +
                            "7 - Веса IV а\n" +
                            "8 - Веса IV b\n" +
                            "9 - Веса IV с\n" +
                            "0 - Закончить просмотр");
            }
        }
    }

    static void consoleOutput(Graph<String> graph) {
        System.out.println(graph);
    }

    static void saveFile(Graph<String> graph, String fileName) {
        if (Objects.isNull(fileName)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите название файла:");
            fileName = scanner.nextLine();
        }
        if (fileName.equals("")) {
            System.out.println("Неверное название файла");
        } else {
            AdditionalClass.writeToFile(graph, fileName);
        }
    }

    static void addEdge(Graph<String> graph) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите название первой вершины:");
            String vertex1 = scanner.nextLine();
            System.out.println("Введите название второй вершины:");
            String vertex2 = scanner.nextLine();

            if (vertex1.equals("") || vertex2.equals("")) {
                System.out.println("Вершины заданы неверно. Выход из программы.");
            } else {
                if (graph.isWeight()) {
                    System.out.println("Введите вес:");
                    Integer weight = scanner.nextInt();
                    try {
                        graph.addEdge(vertex1, vertex2, weight);
                        System.out.println("Ребро добавлено");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    try {
                        graph.addEdge(vertex1, vertex2);
                        System.out.println("Ребро добавлено");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
    }

    static void deleteEdge(Graph graph) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите первую вершину:");
            String vertex1 = scanner.nextLine();
            System.out.println("Введите вторую");
            String vertex2 = scanner.nextLine();

            if (vertex1.equals("") || vertex2.equals("")) {
                System.out.println("Вершины заданы неверно.");
            } else {
                try {
                    graph.deleteEdge(vertex1, vertex2);
                    System.out.println("Ребро удалено");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

    }

    static void addVertex(Graph graph) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название вершины");
        String vertex = scanner.nextLine();

        if (vertex.equals("")) {
            System.out.println("Вершина задана неверно.");
        } else {
            try {
                graph.addVertex(vertex);
                System.out.println("Вершина добавлена");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static void deleteVertex(Graph graph) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название вершины");
        String vertex = scanner.nextLine();

        if (vertex.equals("")) {
            System.out.println("Вершина задана неверно.");
        } else {
            try {
                graph.deleteVertex(vertex);
                System.out.println("Вершина удалена");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
