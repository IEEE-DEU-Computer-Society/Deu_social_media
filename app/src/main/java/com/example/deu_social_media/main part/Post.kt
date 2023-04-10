package com.example.deu_social_media

data class Post(var nick:String,var postText:String,var likeNumber:String,var dislikeNumber:String,var comments:HashMap<String,Any>,
                        var commentsNumber:String,var date:String,var id:String) {

}