import axios from 'axios'

const API_URL = 'http://localhost:8080'

class PostService {
    createPost(postContent){
        return axios.post(`${API_URL}/post-ws/post`,
            {content:postContent}
        );
    }

    getAllPosts(){
        return axios.get(`${API_URL}/post-ws/post`);
    }
}

export default new PostService()