import java.io.*;
import java.util.*;

public class AdditionalClass {

    static Graph readFromFile(String fileName) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream((new FileInputStream(fileName)))) { //try with resources
            Graph graph = (Graph) ois.readObject();
            return graph;

        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Ошибка при загрузке файла");
        }
    }

    static void writeToFile(Graph graph, String fileName) {
        try (ObjectOutputStream obs = new ObjectOutputStream(new FileOutputStream(fileName))) {
            obs.writeObject(graph);
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
    }
    
    static Graph copyGraph(Graph<String> oldGraph) {
        Graph<String> outputGraph = new Graph();
        
        outputGraph.setOrientation(oldGraph.isOrientation());
        outputGraph.setWeight(outputGraph.isWeight());

        Map<String, Map<String, Integer>> newAdjList = new HashMap<>();
        Map<String, Map<String, Integer>> f = oldGraph.getAdjList();

        for (Map.Entry<String, Map<String, Integer>> entry:
                f.entrySet()) {
            Map<String, Integer> copy = new HashMap<>();
            for (Map.Entry<String, Integer> innerEntry:
                    entry.getValue().entrySet()) {
                copy.put(innerEntry.getKey(), innerEntry.getValue());
            }
            newAdjList.put(entry.getKey(), copy);
        }

        outputGraph.setAdjList(newAdjList);

        return outputGraph;
    }

    static boolean checkingForIsolation(String vertex, Graph<String> graph) {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String,Map<String, Integer>> entry:
             graph.getAdjList().entrySet()) {
            if (graph.getAdjList().get(vertex).containsKey(entry.getKey())) {
                list.add(entry.getKey());
            }
        }

        if (list.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
