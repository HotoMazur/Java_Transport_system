package com.example.transport.fxControllers;

import com.example.transport.utils.DbUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import model.Comment;
import model.Forum;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ForumPage implements Initializable {

    @FXML
    public ListView<Forum> listForum;
    @FXML
    public TreeView<Comment> commentTree;
    @FXML
    public TextArea commentBody;

    private Comment selectedComment = null;

    public void updateTitlesOnForum() throws SQLException {
        Forum selectedTitle = listForum.getSelectionModel().getSelectedItem();
        Connection conn = DbUtils.connectToDb();
        PreparedStatement psUpdateTitle = conn.prepareStatement("UPDATE Forum SET title=? WHERE id=?");
        psUpdateTitle.setString(1, commentBody.getText());
        psUpdateTitle.setInt(2, selectedTitle.getId());
        psUpdateTitle.executeUpdate();
        DbUtils.disconnection(conn, psUpdateTitle);
        listForum.setItems(getDataForum());
    }


    public void addNewTitlesOnForum() throws SQLException {
        Connection conn = DbUtils.connectToDb();
        PreparedStatement psTitle = conn.prepareStatement("INSERT INTO Forum(title) VALUE (?)");
        psTitle.setString(1, commentBody.getText());
        psTitle.execute();
        DbUtils.disconnection(conn, psTitle);
        listForum.setItems(getDataForum());
    }

    public void deleteTitlesOnForum() throws SQLException {
        Forum selectedTitle = listForum.getSelectionModel().getSelectedItem();
        Connection conn = DbUtils.connectToDb();
        PreparedStatement psDeleteTitle = conn.prepareStatement("DELETE FROM Forum WHERE id=?");
        psDeleteTitle.setInt(1, selectedTitle.getId());
        psDeleteTitle.executeUpdate();
        DbUtils.disconnection(conn, psDeleteTitle);
        listForum.setItems(getDataForum());
    }

    public static ObservableList<Forum> getDataForum() throws SQLException {
        Connection conn = DbUtils.connectToDb();
        ObservableList<Forum> list = FXCollections.observableArrayList();
        PreparedStatement psForum = conn.prepareStatement("SELECT * FROM Forum");
        ResultSet rs = psForum.executeQuery();
        while (rs.next()) {
            list.add(new Forum(rs.getInt("id"), rs.getString("title")));
        }
        DbUtils.disconnection(conn, psForum);
        return list;
    }

    public void addComment() throws SQLException {
        Forum selectedForum = listForum.getSelectionModel().getSelectedItem();
        if (selectedForum == null) {
            System.out.println(selectedForum);
            return;
        }
        TreeItem<Comment> selectedCommentTreeItem = commentTree.getSelectionModel().getSelectedItem();
        if (selectedCommentTreeItem != null) {

            Connection conn = DbUtils.connectToDb();
            PreparedStatement psCommentChild = conn.prepareStatement("INSERT INTO Comment(text, comment_id) VALUE (?,?)");
            psCommentChild.setString(1, commentBody.getText());
            psCommentChild.setInt(2, selectedCommentTreeItem.getValue().getId());
            psCommentChild.execute();
            DbUtils.disconnection(conn, psCommentChild);

        } else {
            Connection conn = DbUtils.connectToDb();
            PreparedStatement psCommentParent = conn.prepareStatement("INSERT INTO Comment(text, title_id) VALUE (?,?)");
            psCommentParent.setString(1, commentBody.getText());
            psCommentParent.setInt(2, selectedForum.getId());
            System.out.println(selectedForum.getId());
            psCommentParent.execute();
            DbUtils.disconnection(conn, psCommentParent);
        }
        refreshCommentTree(selectedForum);
    }

    public void goToComments() {
        Forum selectedForum = listForum.getSelectionModel().getSelectedItem();
        if (selectedForum != null) {
            refreshCommentTree(selectedForum);
        }
    }

    public void selectComment() {
        selectedComment = commentTree.getSelectionModel().getSelectedItem().getValue();
    }

    private void refreshCommentTree(Forum forum) {
        if (forum == null) {
            return;
        }
        try {
            Connection connection = DbUtils.connectToDb();
            String forumTitle = forum.getTitle();
            String rootCommentQuery = "SELECT * FROM Comment WHERE title_id = ?";
            PreparedStatement rootCommentStmt = connection.prepareStatement(rootCommentQuery);
            rootCommentStmt.setInt(1, forum.getId());
            ResultSet rootCommentResult = rootCommentStmt.executeQuery();
            TreeItem<Comment> root = new TreeItem<>(new Comment("Comments for " + forumTitle));
            while (rootCommentResult.next()) {
                int rootCommentId = rootCommentResult.getInt("id");
                String rootCommentText = rootCommentResult.getString("text");
                Comment rootComment = new Comment(rootCommentId, rootCommentText);
                TreeItem<Comment> rootCommentNode = new TreeItem<>(rootComment);
                String childCommentQuery = "SELECT * FROM Comment WHERE comment_id = ?";
                PreparedStatement childCommentStmt = connection.prepareStatement(childCommentQuery);
                childCommentStmt.setInt(1, rootCommentId);
                ResultSet childCommentResult = childCommentStmt.executeQuery();
                while (childCommentResult.next()) {
                    int childCommentId = childCommentResult.getInt("id");
                    String childCommentText = childCommentResult.getString("text");
                    Comment childComment = new Comment(childCommentId, childCommentText);
                    TreeItem<Comment> childCommentNode = new TreeItem<>(childComment);
                    rootCommentNode.getChildren().add(childCommentNode);
                }
                root.getChildren().add(rootCommentNode);
            }
            commentTree.setRoot(root);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateComment() throws SQLException {
        TreeItem<Comment> selectedCommentTreeItem = commentTree.getSelectionModel().getSelectedItem();
        if (selectedCommentTreeItem == null) {
            return;
        }
        Forum selectedForum = listForum.getSelectionModel().getSelectedItem();

        Connection conn = DbUtils.connectToDb();
        PreparedStatement psUpdateComment = conn.prepareStatement("UPDATE Comment SET text=? WHERE id=?");
        psUpdateComment.setString(1, commentBody.getText());
        psUpdateComment.setInt(2, selectedCommentTreeItem.getValue().getId());
        psUpdateComment.execute();
        DbUtils.disconnection(conn, psUpdateComment);

        refreshCommentTree(selectedForum);
    }


    public void deleteComment() throws SQLException {
//        ObservableList<Integer> selectedCommentIndices = commentTree.getSelectionModel().getSelectedIndices();
//        for (int i = selectedCommentIndices.size() - 1; i >= 0; i--) {
//            int index = selectedCommentIndices.get(i);
//            TreeItem<Comment> item = commentTree.getTreeItem(index);
//            if (item != null) {
//                if (item.getParent() == null) {
//                    deleteComment(item);
//                } else {
//                    item.getParent().getChildren().remove(item);
//                }
//            }
//        }
//        commentTree.refresh();
        TreeItem<Comment> selectedCommentTreeItem = commentTree.getSelectionModel().getSelectedItem();
        if (selectedCommentTreeItem == null) {
            return;
        }
        Forum selectedForum = listForum.getSelectionModel().getSelectedItem();


        Connection conn = DbUtils.connectToDb();
        PreparedStatement psDeleteComment = conn.prepareStatement("DELETE FROM Comment WHERE id=? OR comment_id=?");
        psDeleteComment.setInt(1, selectedCommentTreeItem.getValue().getId());
        psDeleteComment.setInt(2, selectedCommentTreeItem.getValue().getId());
        psDeleteComment.execute();
        DbUtils.disconnection(conn, psDeleteComment);

        refreshCommentTree(selectedForum);
    }

    private void deleteComment(TreeItem<Comment> comment) {
        if (comment == null) {
            return;
        }
        if (comment.getParent() != null) {
            for (TreeItem<Comment> child : comment.getChildren()) {
                deleteComment(child);
            }
            comment.getParent().getChildren().remove(comment);
        } else {
            commentTree.getRoot().getChildren().remove(comment);
        }
        commentTree.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            listForum.setItems(getDataForum());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}