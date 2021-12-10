import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Graph<T extends Comparable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1049801185938396299L;
    private boolean weight;
    private boolean orientation;
    private Map<T, Map<T, Integer>> adjList;

    public Graph(boolean weight, boolean orientation) {
        this.weight = weight;
        this.orientation = orientation;
        adjList = new HashMap<>();
    }

    public Graph() {
        this(false, false);
    }

    public Graph(String fileName) {
        Graph graph = null;
        try {
            graph = AdditionalClass.readFromFile(fileName);
            this.orientation = graph.isOrientation();
            this.weight = graph.isWeight();
            this.adjList = graph.getAdjList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Graph(Graph oldGraph) {
        Graph<T> copyGraph = AdditionalClass.copyGraph(oldGraph);

        orientation = copyGraph.isOrientation();
        weight = copyGraph.isWeight();
        adjList = copyGraph.getAdjList();
    }

    public boolean isWeight() {
        return weight;
    }

    public void setWeight(boolean weight) {
        this.weight = weight;
    }

    public boolean isOrientation() {
        return orientation;
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    public Map<T, Map<T, Integer>> getAdjList() {
        return adjList;
    }

    public void setAdjList(Map<T, Map<T, Integer>> adjList) {
        this.adjList = adjList;
    }

    public Map<T, Map<T, Integer>> getCopyAdjList() { return new HashMap<>(adjList); }

    public void addVertex(T vertex) throws Exception {
        if (!adjList.containsKey(vertex)) {
            adjList.put(vertex, new HashMap<>());
        } else {
            throw new Exception("Заданная вершина уже существует");
        }
    }

    public void deleteVertex(T vertex) throws Exception {
        if (!adjList.containsKey(vertex)) {
            throw new Exception("Заданной вершины не существует");
        }
        adjList.remove(vertex);

        for (T key :
                adjList.keySet()) {
            if (adjList.get(key).containsKey(vertex)) {
                adjList.get(key).remove(vertex);
            }
        }
    }

    public void addEdge(T vertex1, T vertex2, Integer weight) throws Exception {
        if (!adjList.containsKey(vertex1)) {
            addVertex(vertex1);
        }
        if (!adjList.containsKey(vertex2)) {
            addVertex(vertex2);
        }

        if (!adjList.containsKey(vertex1) || !adjList.containsKey(vertex2)) {
            throw new Exception("Неправильно заданы вершины");
        }

        String message = "";
        if (adjList.get(vertex1).containsKey(vertex2)) {
            if (isWeight()) {
                adjList.get(vertex1).put(vertex2, weight);
                if (!isOrientation()) {
                    adjList.get(vertex2).put(vertex1, weight);
                }
                message = "Вес обновлен";
            }
            throw new Exception("Данный путь уже существует" + message);
        }

        adjList.get(vertex1).put(vertex2, weight);
        if (!isOrientation()) {
            adjList.get(vertex2).put(vertex1, weight);
        }
    }

    public void addEdge(T vertex1, T vertex2) throws Exception {
        addEdge(vertex1, vertex2, -1);
    }

    public void deleteEdge(T vertex1, T vertex2) throws Exception {
        if (!adjList.containsKey(vertex1) || !adjList.containsKey(vertex2)) {
            throw new Exception("Неправильно заданы вершины");
        }

        if (adjList.get(vertex1).containsKey(vertex2) || adjList.get(vertex2).containsKey(vertex1)) {
            if (adjList.get(vertex1).containsKey(vertex2)) {
                adjList.get(vertex1).remove(vertex2);
                if (!isOrientation()) {
                    adjList.get(vertex2).remove(vertex1);
                }
            }
            if (adjList.get(vertex2).containsKey(vertex1)) {
                adjList.get(vertex2).remove(vertex1);
                if (!isOrientation()) {
                    adjList.get(vertex1).remove(vertex2);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        StringBuilder w;
        StringBuilder o;
        if (isWeight()) {
            w = new StringBuilder("взвешенный");
        } else {
            w = new StringBuilder("невзвешенный");
        }

        if (isOrientation()) {
            o = new StringBuilder("ориентированный");
        } else {
            o = new StringBuilder("неориентированный");
        }
        output.append("Граф ").append(w).append(", ").append(o).append(".\nВершины:\n");
        for (Map.Entry<T, Map<T, Integer>> k :
                adjList.entrySet()) {
            output.append('\t').append(k.getKey().toString()).append(" : ").append('\n');
            for (Map.Entry<T, Integer> nk : k.getValue().entrySet()) {
                output.append("\t\t").append(nk.getKey());
                if (this.isWeight()) {
                    output.append(" - ").append(nk.getValue());
                }
                output.append("\n");
            }
            output.append("\n");
        }

        return output.toString();
    }

    private Map<T, Boolean> nov = new HashMap<>();

    // помечаем все вершины как непросмотренные
    public void novSet() {
        for (Map.Entry<T, Map<T, Integer>> entry:
                adjList.entrySet()) {
            nov.put(entry.getKey(), true);
        }
    }

    public void dfs(T vertex) throws Exception {

        if (!adjList.containsKey(vertex)) {
            throw new Exception("Такой вершины не существует");
        }

        nov.put(vertex, false);
        for (Map.Entry<T, Map<T, Integer>> entry:
                adjList.entrySet()) {
            for (Map.Entry<T, Integer> innerEntry:
                    entry.getValue().entrySet()) {
                if (adjList.get(vertex).containsKey(innerEntry.getKey()) && nov.get(innerEntry.getKey())) {
                    System.out.println("\t" + innerEntry.getKey());
                    dfs(innerEntry.getKey());
                }
            }
        }
    }

    public int bfs(T vertex) throws Exception {

        if (!adjList.containsKey(vertex)) {
            throw new Exception("Такой вершины не существует");
        }

        Queue queue = new LinkedList();
        queue.add(vertex);
        nov.put(vertex, false);
        int count = 0;
        int max = 0;

        while (!queue.isEmpty()) {
            vertex = (T) queue.poll();

            if (count > max) {
                max = count;
            }

            for (Map.Entry<T, Map<T, Integer>> entry :
                    adjList.entrySet()) {
                if (adjList.get(vertex).containsKey(entry.getKey()) && nov.get(entry.getKey())) {
                    count++;
                    queue.add(entry.getKey());
                    nov.put(entry.getKey(), false);
                }
            }
        }
        return max;
    }

    private class StringComparator implements Comparator<String> {
        private Map<T, Map<T, Integer>> newAdjList;

        public StringComparator(Graph graph) {
            this.newAdjList = getCopyAdjList();
        }

        @Override
        public int compare(String o1, String o2) {
            int weight1 = newAdjList.get(o1.split("-")[0]).get(o1.split("-")[1]);
            int weight2 = newAdjList.get(o2.split("-")[0]).get(o2.split("-")[1]);

            if (weight1 > weight2) {
                return 1;
            } else if (weight1 < weight2) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public List<String> kruskal() {

        List<String> edged = new ArrayList<>();

        for (Map.Entry<T, Map<T, Integer>> entry:
             adjList.entrySet()) {
            for (Map.Entry<T, Integer> innerEntry:
                 entry.getValue().entrySet()) {
                edged.add(entry.getKey().toString() + "-" + innerEntry.getKey().toString());
            }
        }

        edged.sort(new StringComparator(this));

        Map<T, Integer> components = new HashMap<>();
        Integer count = 0;
        for (Map.Entry<T, Map<T, Integer>> entry:
             adjList.entrySet()) {
            components.put(entry.getKey(), count);
            count++;
        }

        List<String> output = new ArrayList<>();

        for (String edge:
             edged) {
            String[] edg = edge.split("-");
            String start = edg[0];
            String end = edg[1];

            if (!Objects.equals(components.get(start), components.get(end))) {
                output.add(edge);
                int a = components.get(start);
                int b = components.get(end);
                for (Map.Entry<T, Map<T, Integer>> innerEntry:
                     adjList.entrySet()) {
                    if (components.get(innerEntry.getKey()) == b) {
                        components.put(innerEntry.getKey(), a);
                    }
                }
            }
        }

        return output;
    }

    private Map<T, Integer> d = new LinkedHashMap<>();

    public void setD() {
        for (Map.Entry<T, Map<T, Integer>> entry:
             adjList.entrySet()) {
            d.put(entry.getKey(), Integer.MAX_VALUE);
        }
    }

    public Integer dijkstra(T vertex) {

        d.put(vertex, 0);

        nov.put(vertex, false);

        for (Map.Entry<T, Map<T, Integer>> entry:
             adjList.entrySet()) {
            while (entry.getKey().equals(vertex)) {
                for (Map.Entry<T, Integer> innerEntry :
                        entry.getValue().entrySet()) {
                    if (adjList.get(entry.getKey()).containsKey(innerEntry.getKey())) {
                        if (d.get(innerEntry.getKey()) > d.get(entry.getKey()) + innerEntry.getValue()) {
                            d.put(innerEntry.getKey(), d.get(entry.getKey()) + innerEntry.getValue());
                        }
                    }
                }
                break;
            }
        }

        List<Map.Entry<T, Integer>> sortedList = new ArrayList<>(d.entrySet());

        Collections.sort(sortedList, new Comparator<Map.Entry<T, Integer>>() {
            @Override
            public int compare(Map.Entry<T, Integer> o1, Map.Entry<T, Integer> o2) {
                return o1.getValue()-o2.getValue();
            }
        });


        for (Map.Entry<T, Integer> sortedEntry:
             sortedList) {
            if (!sortedEntry.getKey().equals(vertex)) {
                for (Map.Entry<T, Map<T, Integer>> entry :
                        adjList.entrySet()) {
                    if (sortedEntry.getKey().equals(entry.getKey())) {
                        for (Map.Entry<T, Integer> innerEntry :
                                entry.getValue().entrySet()) {
                            if (adjList.get(entry.getKey()).containsKey(innerEntry.getKey()) && nov.get(innerEntry.getKey())) {
                                if (d.get(innerEntry.getKey()) > d.get(entry.getKey()) + innerEntry.getValue()) {
                                    d.put(innerEntry.getKey(), d.get(entry.getKey()) + innerEntry.getValue());
                                    nov.put(entry.getKey(), false);
                                }
                            }
                        }
                    }
                }
            }
        }


        int min = Integer.MAX_VALUE;
        for (Map.Entry<T, Integer> entry:
             d.entrySet()) {
            if (entry.getValue() < min && !entry.getKey().equals(vertex) && !nov.get(entry.getKey())) {
                min = entry.getValue();
            }
        }

        return min;
    }

    public Map<T, Integer> bellmanFord(T vertex) throws Exception {

        if (!adjList.containsKey(vertex)) {
            throw new Exception("Вершина задана неверно");
        }

        d.put(vertex, 0);

        for (Map.Entry<T, Map<T, Integer>> entry :
                adjList.entrySet()) {
            for (Map.Entry<T, Integer> innerEntry :
                    entry.getValue().entrySet()) {
                if (d.get(entry.getKey()) < Integer.MAX_VALUE) {
                    if (d.get(innerEntry.getKey()) > (d.get(entry.getKey()) + innerEntry.getValue())) {
                        d.put(innerEntry.getKey(), d.get(entry.getKey()) + innerEntry.getValue());
                    }
                }
            }
        }
        return d;
    }

    public void floyd(T vertex) {

    }
}