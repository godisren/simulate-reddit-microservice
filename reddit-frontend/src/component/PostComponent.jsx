import React from 'react';
import Moment from 'react-moment';
import PostService from '../service/PostService';

class PostList extends React.Component {
    render(){
  
      const postItems = this.props.posts.concat().reverse().map((post)=>
        <div className="card mt-2" key={post.id}>
          <h5 className="card-header text-right">
            <Moment format="YYYY/MM/DD HH:mm:ss">{post.createdDate}</Moment><span> </span>
            created by {post.createdBy}
          </h5>
          <div className="card-body">
            <p className="card-text">{post.content}</p>
          </div>
        </div>
      );
  
      return (
        <>
          { (postItems==null || postItems.length===0) && <p>Empty Post</p>}
          {postItems}
        </>
      )
    }
  }
  
  class ConetntPost extends React.Component {
  
    constructor(props) {
      super(props);
      this.handleSubmit = this.handleSubmit.bind(this);
    }
    
    handleSubmit(event){
      event.preventDefault();
  
      let postContent =this.refs.content.value;
      let _this = this;
  
      if(!window.confirm('submit ?')){
        return;
      }
  
      PostService.createPost(postContent)
        .then((response) => {
          
          _this.props.onAfterSubmit(response.data);
          _this.refs.content.value="";
        }).catch((error) => {
          alert(error);
      });
    }
  
    render(){
      return (
        <div className="card mt-2">
          <h5 className="card-header bg-primary text-white">Create a Post</h5>
          <div className="card-body">
          <form onSubmit={this.handleSubmit}>
            <p className="card-text">
                <textarea ref="content" className="form-control" ></textarea><br/>
                <button className="btn btn-primary">submit</button>
            </p>
            </form>
          </div>
        </div>
      )
    }
  }
  
  class PostController extends React.Component {
    constructor(props){
      super(props)
      this.state = {posts:[]}
      this.handleSubmitContent = this.handleSubmitContent.bind(this);
    }

    componentDidMount(){
      let _this = this;
      
      PostService.getAllPosts()
      .then(function (response) {
        _this.setState({posts:response.data})
      })
      .catch(function (error) {
        alert(error);
      });
    }
  
    handleSubmitContent(content){
      this.setState({posts: this.state.posts.concat(content)})
    }
  
    render(){
      let posts = this.state.posts;
  
      return (
        <>
        <h2>Post</h2>
        <div className="row">
          <div className="col-sm-7">
            <PostList posts={posts} />
          </div>
          <div className="col-sm-5">
            <ConetntPost onAfterSubmit={this.handleSubmitContent}/>
          </div>
        </div>
        </>
      );
    }
  }

export { PostController};