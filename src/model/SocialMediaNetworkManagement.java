package model;

import java.util.*;

public class SocialMediaNetworkManagement {
    private final Graph graphFriend;
    private final Graph graphRequest;
    private int id;
    private Node currentUser;
    private ArrayList<ArrayList<String>> listUsername;
    private ArrayList<ArrayList<Integer>> relationship;

    public SocialMediaNetworkManagement() {
        this.graphFriend = new Graph();
        this.graphRequest = new Graph();
        this.id = 0;
        this.currentUser = null;
        this.listUsername = new ArrayList<>();
        this.relationship = new ArrayList<>();
    }

    public String getFileNameFriend() {
        return "src\\data\\FriendData.txt";
    }

    public String getFileNameRequest() {
        return "src\\data\\RequestData.txt";
    }

    public String getFileNameUsername() {
        return "src\\data\\UsernameData.txt";
    }

    public String getFileNameRelationship() {
        return "src\\data\\RelationshipData.txt";
    }

    public ArrayList<ArrayList<Integer>> getRelationship() {
        return relationship;
    }

    public void setRelationship(ArrayList<ArrayList<Integer>> relationship) {
        this.relationship = relationship;
    }

    public Graph getGraphFriend() {
        return graphFriend;
    }

    public Graph getGraphRequest() {
        return graphRequest;
    }

    public ArrayList<ArrayList<String>> getListUsername() {
        return listUsername;
    }

    public void setListUsername(ArrayList<ArrayList<String>> listUsername) {
        this.listUsername = listUsername;
    }

    public Node getCurrentUser() {
        return currentUser;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void createUser(Node user) {
        user.setId(id);
        this.graphFriend.addNode(user);
        this.graphRequest.addNode(user);
        this.id++;
        this.addUsername(user.getData(), user.getId());
    }

    public void addUsername(String username, int id) {
        String[] str = username.split(" ");

        for (int i = 0; i < str.length; i++) {
            String element = str[i] + " - " + id;
            if (this.listUsername.size() == i) {
                this.listUsername.add(new ArrayList<>());
                this.listUsername.get(i).add(element);
            } else {
                int idx = FindIndexInsert.findIndex(this.listUsername.get(i), element);
                this.listUsername.get(i).add(idx, element);
            }
        }
    }

    public String connectedComponents() {
        UnionFind unionFind = new UnionFind(this.graphFriend.getAdjList().size());
        Map<Integer, List<Integer>> map = unionFind.connectedComponents(this.relationship);
        int count = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            List<Integer> list = entry.getValue();
            sb.append("G").append(count).append(" = {");
            for (int i : list) {
                sb.append(this.graphFriend.getAdjList().get(i).get(0)).append(", ");
            }
            sb.append("}");
            sb.append("<br/>");
            count++;
        }
        sb.append("<html>");
        return sb.toString();
    }

    public void choiceUser(int id) {
        this.currentUser = this.graphFriend.getAdjList().get(id).get(0);
    }

    public Node getUserById(int id) {
        return this.graphFriend.getAdjList().get(id).get(0);
    }

    public void addFriend(int idDst) {
        this.graphFriend.addEdge(this.currentUser.getId(), idDst);
        this.graphFriend.addEdge(idDst, this.currentUser.getId());
        ArrayList<Integer> newRelationship = new ArrayList<>();
        newRelationship.add(this.currentUser.getId());
        newRelationship.add(idDst);
        this.relationship.add(newRelationship);
    }

    public List<Node> getListFriend() {
        List<Node> listFriend = this.graphFriend.getAdjList().get(this.currentUser.getId());
        return listFriend.subList(1, listFriend.size());
    }

    public void addRequest(int id) {
        if (this.graphFriend.checkEdge(id, this.currentUser.getId()) ||
                this.graphRequest.checkEdge(id, this.currentUser.getId()) ||
                this.graphRequest.checkEdge(this.currentUser.getId(), id)) {
            return;
        }
        this.graphRequest.addEdge(id, this.currentUser.getId());
    }

    public boolean isFriend(int id) {
        return this.graphFriend.checkEdge(id, this.currentUser.getId());
    }

    public List<Node> getListRequest() {
        List<Node> request = this.graphRequest.getAdjList().get(this.currentUser.getId());
        request = request.subList(1, request.size());
        Collections.reverse(request);
        return request;
    }

    public void removeRequest(int id) {
        this.graphRequest.removeEdge(this.currentUser.getId(), id);
    }

    public void addContent(Content content) {
        if (SearchContent.search(content.getData(), "$") == 1) {
            this.currentUser.getContentList().add(content);
        }
    }

    public void removeFriend(int idDst) {
        this.graphFriend.removeEdge(this.currentUser.getId(), idDst);
        this.graphFriend.removeEdge(idDst, this.currentUser.getId());
    }

    public List<Content> getListContent() {
        List<List<Content>> contentList = new ArrayList<>();
        contentList.add(this.currentUser.getContentList());

        List<Node> listFriend = this.getListFriend();
        for (Node node : listFriend) {
            contentList.add(node.getContentList());
        }

        return MergeContent.mergeKLists(contentList, contentList.size() - 1);
    }

    public int getNumberMutualFriends(int idUser1, int idUser2) {
        List<Node> friendsUser1 = this.graphFriend.getAdjList().get(idUser1);
        List<Node> friendsUser2 = this.graphFriend.getAdjList().get(idUser2);

        HashSet<String> set1 = new HashSet<>();
        for (int i = 1; i < friendsUser1.size(); i++) {
            set1.add(friendsUser1.get(i).getData());
        }

        HashSet<String> set2 = new HashSet<>();
        for (int i = 1; i < friendsUser2.size(); i++) {
            set2.add(friendsUser2.get(i).getData());
        }

        int count = 0;
        for (String str : set1) {
            if (set2.contains(str)) {
                count++;
            }
        }
        return count;
    }

    private static class priorityNode implements Comparable<priorityNode> {
        Node user;
        int priority;

        public priorityNode(Node user, int priority) {
            this.user = user;
            this.priority = priority;
        }


        @Override
        public int compareTo(priorityNode o) {
            return o.priority - this.priority;
        }
    }

    public ArrayList<Node> getSuggestFriend(int src, int quantity) {
        Queue<Node> queue = this.graphFriend.breadthFirstSearch(src, 2);
        System.out.println(queue);

        Queue<priorityNode> priorityNodeQueue = new PriorityQueue<>();
        while (!queue.isEmpty()) {
            Node user = queue.poll();
            priorityNodeQueue.offer(new priorityNode(user, getNumberMutualFriends(src, user.getId())));
        }

        ArrayList<Node> list = new ArrayList<>();
        while (!priorityNodeQueue.isEmpty() && list.size() < quantity) {
            list.add(priorityNodeQueue.poll().user);
        }
        System.out.println(list);
        return list;
    }

    public ArrayList<Node> getSearchFriend(String name, int quantity) {
        int[] distance = this.graphFriend.shortestPath(this.currentUser.getId());

        Queue<priorityNode> priorityNodeQueue = new PriorityQueue<>(Collections.reverseOrder());

        System.out.println(this.listUsername);
        int length = name.split(" ").length;

        Map<Integer, Integer> map = SearchUser.getIdSearchUser(name, this.listUsername);
        for (Map.Entry<Integer, Integer> mapElement : map.entrySet()) {
            if (mapElement.getValue() >= length) {
                priorityNodeQueue.offer(new priorityNode(
                        this.graphFriend.getAdjList().get(mapElement.getKey()).get(0),
                        distance[mapElement.getKey()]));
            }
        }

        ArrayList<Node> listSearch = new ArrayList<>();
        while (!priorityNodeQueue.isEmpty() && listSearch.size() < quantity) {
            listSearch.add(priorityNodeQueue.poll().user);
        }

        return listSearch;
    }

    private static class priorityContent implements Comparable<priorityContent> {
        Content content;
        int priority;

        public priorityContent(Content content, int priority) {
            this.content = content;
            this.priority = priority;
        }


        @Override
        public int compareTo(priorityContent o) {
            return o.priority - this.priority;
        }
    }

    public List<Content> getListSearchContent(String pattern, int quantity) {
        Queue<priorityContent> priorityContents = new PriorityQueue<>();

        for (int i = 0; i < this.graphFriend.getAdjList().size(); i++) {
            List<Content> contentList = getUserById(i).getContentList();
            for (Content content : contentList) {
                int count = SearchContent.search(content.getData(), pattern);
                if (count > 0) {
                    priorityContents.offer(new priorityContent(
                            content, count));
                }
            }
        }

        List<Content> list = new ArrayList<>();
        while (!priorityContents.isEmpty() && list.size() < quantity) {
            list.add(priorityContents.poll().content);
        }

        return list;
    }
}