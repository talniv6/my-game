package com.talniv.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.talniv.game.utils.CyclicArray;

public class TextGraph {

    class Edge{
        String text;
        Node a,b;

        public Edge(String text, Node a, Node b) {
            this.text = text;
            this.a = a;
            this.b = b;
        }
    }

    class Node{
        String text;
        int id;
        CyclicArray<Edge> outgoings;

        Node(String text, int id) {
            this.text = text;
            outgoings = new com.talniv.game.utils.CyclicArray<Edge>();
            this.id = id;
        }

        void addOutgoingEdge(Edge edge){
            outgoings.add(edge);
        }
    }

    private Array<Node> nodes;
    private Node curNode;
    private boolean finished;
    private Array<Integer> path;

    public TextGraph(String path) {
        nodes = new Array<Node>();
        this.path = new Array<Integer>();
        finished = false;
        create(path);
        curNode = nodes.get(0);
        this.path.add(curNode.id);
    }

    public void next(){
        if (finished){
            return;
        }
        if (last()){
            finished = true;
            return;
        }
        curNode = curNode.outgoings.getCurrent().b;
        path.add(curNode.id);
    }

    public void add(Node node){
        nodes.add(node);
    }

    private Node createNode(String text, int id){
        Node node = new Node(text, id);
        nodes.add(node);
        return node;
    }

    private void addEdgeToNode(Node a, Node b, String text){
        a.addOutgoingEdge(new Edge(text, a, b));
    }

    public Node getCurNode() {
        return curNode;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean last(){
        return curNode.outgoings.size() == 0;
    }

    public boolean isQuestion(){
        return curNode.outgoings.size()>1;
    }

    public void reset(){
        finished = false;
        curNode = nodes.first();
        for (Node node : nodes){
            node.outgoings.selectFirst();
        }
        path.clear();
        path.add(curNode.id);
    }

    public Node getNode(int index){
        return nodes.get(index);
    }

    public Array<Integer> getPath() {
        return path;
    }

    public boolean arePathsEqual(int[] expectedPath){
        if (path.size != expectedPath.length) {
            return false;
        }
        for (int i = 0; i < expectedPath.length; i++) {
            if (expectedPath[i] != path.get(i)) {
                return false;
            }
        }
        return true;
    }

    public void create(String path){
        FileHandle info = Gdx.files.internal(path);
        String[] lines = info.readString().split("\\r\\n");
        int nodesCounter=0;

        int i = 0;
        while (i < lines.length && !lines[i].equals("->")){
            if (!lines[i].equals("")){
                createNode(lines[i], nodesCounter);
                nodesCounter++;
            }
            i++;
        }
        i++;
        while (i < lines.length){
            if (lines[i].equals("")){
                i++;
                continue;
            }
            String edgeText = "";
            int a = Integer.parseInt(lines[i].split(",")[0]), b = Integer.parseInt(lines[i].split(",")[1]);
            if (i+1 < lines.length){
                edgeText = lines[i+1];
            }
            addEdgeToNode(nodes.get(a), nodes.get(b), edgeText);
            i +=2 ;
        }
    }
}
