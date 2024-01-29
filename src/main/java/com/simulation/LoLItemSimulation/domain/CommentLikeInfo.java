package com.simulation.LoLItemSimulation.domain;

public class CommentLikeInfo {
    private Comment comment;
    private boolean isLiked;

    public CommentLikeInfo(Comment comment, boolean isLiked) {
        this.comment = comment;
        this.isLiked = isLiked;
    }

    // Getter 및 Setter 생략


    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}