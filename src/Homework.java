import java.io.Console;
import java.util.*;
import java.util.stream.Collectors;

public class Homework {

    static void Ia1(Graph<String> graph) {
        if (!graph.isOrientation()) {
            System.out.println("Граф неориентированный. Задача не решаема. Выход из программы");
            return;
        }

        System.out.println("Текст задания:\n" +
                "8. Вывести те вершины, у которых полустепень исхода больше полустепени захода.");

        List<String> output = new ArrayList<>();
        Map<String, Map<String, Integer>> copyList = graph.getAdjList();

        int countExodus;
        int countOrigin;

        for (Map.Entry<String, Map<String, Integer>> entry :
                copyList.entrySet()) {
            countExodus = 0;
            countOrigin = 0;
            for (Map.Entry<String, Integer> innerEntry :
                    entry.getValue().entrySet()) {
                if (copyList.get(entry.getKey()).containsKey(innerEntry.getKey())) {
                    countExodus++;
                }
            }

            for (Map.Entry<String, Map<String, Integer>> newEntry :
                    copyList.entrySet()) {
                if (copyList.get(newEntry.getKey()).containsKey(entry.getKey())) {
                    countOrigin++;
                }
            }
            if (countExodus > countOrigin) {
                output.add(entry.getKey());
            }
        }
        for (String s : output) {
            System.out.print(s + " ");
        }
    }

    static void Ia2(Graph<String> graph) {
        System.out.println("Текст задания:\n" +
                "20. Вывести все вершины орграфа, не смежные с данной.");
        System.out.println("Введите вершину");
        Scanner scanner = new Scanner(System.in);
        String vertex = scanner.nextLine();

        if (vertex.equals("")) {
            System.out.print("Вершина задана неверно. Для выхода из программы нажмите Enter");
            return;
        }

        List<String> output = new ArrayList<>();
        Map<String, Map<String, Integer>> copyList = graph.getAdjList();
        if (!copyList.containsKey(vertex)) {
            System.out.print("Такой вершины не существует. Для выхода из программы нажмите Enter");
            return;
        }

        for (Map.Entry<String, Map<String, Integer>> entry:
             copyList.entrySet()) {
            if (!copyList.get(vertex).containsKey(entry.getKey())) {
                output.add(entry.getKey());
            }
        }
        System.out.println("Вершины, несмежные с вершиной " + vertex + ":");
        for (String s : output) {
            System.out.print(s + " ");
        }
    }

    static void Ib(Graph<String> graph) {
        System.out.println("""

                Текст задания:
                4. Построить орграф, являющийся обращением данного орграфа (каждая дуга перевёрнута).""");

        if (!graph.isOrientation()) {
            System.out.println("Граф неориентированный. Выход из программы");
            return;
        }

        System.out.println("Данный граф: \n" +
                graph);

        Graph<String> outputGraph = new Graph<>(graph);

        for (Map.Entry<String, Map<String, Integer>> entry:
             graph.getAdjList().entrySet()) {
            for (Map.Entry<String, Integer> innerEntry:
                 entry.getValue().entrySet()) {
                if (graph.getAdjList().get(entry.getKey()).containsKey(innerEntry.getKey())) {
                    try {
                        outputGraph.deleteEdge(entry.getKey(), innerEntry.getKey());
                        outputGraph.addEdge(innerEntry.getKey(), entry.getKey());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("\nОбращение данного графа: \n" +
                outputGraph);
    }

    static void II1(Graph<String> graph) {
        System.out.println("""

                Текст задания:
                1. Найти все вершины орграфа, из которых существует путь в данную.""");

        if (!graph.isOrientation()) {
            System.out.println("Граф неориентированный. Выход из программы");
            return;
        }

        System.out.println("Введите вершину");
        Scanner scanner = new Scanner(System.in);
        String vertex = scanner.nextLine();

        if (vertex.equals("")) {
            System.out.println("Вершина задана неверно. Выход из программы");
            return;
        }

        try {
            graph.novSet();
            System.out.println("\nРешение с помощью алгоритма обхода в глубину:\n");
            System.out.println("Из вершины " + vertex + " достижимы вершины:");
            graph.dfs(vertex);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void II2(Graph<String> graph) {
        System.out.println("""

                Текст задания:
                36.Эксцентриситет вершины — максимальное из всех минимальных (по числу рёбер) расстояний от других вершин до данной вершины. Найти радиус графа — минимальный из эксцентриситетов его вершин.""");

        try {
            System.out.println("\nРешение с помощью алгоритма обхода в ширину:\n");
            List<Integer> list = new ArrayList<>();
            for (Map.Entry<String, Map<String, Integer>> entry:
                 graph.getAdjList().entrySet()) {
                if (AdditionalClass.checkingForIsolation(entry.getKey(), graph)) {
                    graph.novSet();
                    list.add(graph.bfs(entry.getKey()));
                }
            }
            Collections.sort(list);

            System.out.println("Радиус графа равен: " + list.get(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void III(Graph<String> graph) {
        System.out.println("""

                Текст задания:
                Дан взвешенный неориентированный граф из N вершин и M ребер. Требуется найти в нем каркас минимального веса.Алгоритм Краскала""");

        if (!graph.isWeight() || graph.isOrientation()) {
            System.out.println("Граф не удовлетворяет условию задачи. Выход из программы");
            return;
        }

        System.out.println(graph.kruskal());

    }

    static void IVa(Graph<String> graph) {
        System.out.println("""

                Текст задания:
                10. Эксцентриситет вершины — максимальное расстояние из всех минимальных расстояний от других вершин до данной вершины. Найти радиус графа — минимальный из эксцентриситетов его вершин.
                Алгоритм Дейкстры""");

        for (Map.Entry<String, Map<String, Integer>> entry:
             graph.getAdjList().entrySet()) {
            for (Map.Entry<String, Integer> innerEntry:
                 entry.getValue().entrySet()) {
                if (innerEntry.getValue() < 0) {
                    System.out.println("В графе есть дуги с отрицательными весами. Выход из программы");
                    return;
                }
            }
        }

        List<Integer> output = new ArrayList<>();

        for (Map.Entry<String, Map<String, Integer>> entry:
             graph.getAdjList().entrySet()) {
            if (AdditionalClass.checkingForIsolation(entry.getKey(), graph)) {
                graph.novSet();
                graph.setD();
                output.add(graph.dijkstra(entry.getKey()));
            }
        }

        Collections.sort(output);

        System.out.println("Радиус графа: " + output.get(output.size()-1));

    }

    static void IVb(Graph<String> graph) {
        System.out.println("""
                Текст задания:
                14. Вывести все кратчайшие пути из вершины u.
                Алгоритм Беллмана-Форда""");


        System.out.println("\nВведите вершину");
        Scanner scanner = new Scanner(System.in);
        String vertex = scanner.nextLine();
        Map<String, Integer> output = null;
        try {
            graph.setD();
            output = graph.bellmanFord(vertex);
            System.out.println("\nВсе кратчайшие пути из вершины " + vertex + ":");
            for (Map.Entry<String, Integer> entry:
                    output.entrySet()) {
                if (!entry.getKey().equals(vertex)) {
                    System.out.println(entry.getKey() + " " + entry.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    static void IVc(Graph<String> graph) {
        System.out.println("""
                Текст задания
                3. Определить, есть ли в графе вершина, каждая из минимальных стоимостей пути от которой до остальных не превосходит N.
                Алгоритм Флойда""");

        System.out.println("\nВведите N");
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();

        List<String> output = graph.floyd(N);

        if (output.isEmpty()) {
            System.out.println("\nТаких вершин не существует");
        } else {
            System.out.println("\nВершины:");
            for (int i = 0; i < output.size(); i++) {
                System.out.print(output.get(i) + " ");
            }
        }
    }

    static void V(Graph<String> graph) {
        System.out.println("""
                Текст задания:
                Решить задачу на нахождение максимального потока любым алгоритмом""");

        // тут пока хз че делать блинб
    }
}
