import React from 'react';
import { BrowserRouter as Router, Switch, Route, Link, withRouter  } from 'react-router-dom';
import { Home} from './Pages';
import {PostController} from './component/PostComponent.jsx';
import LoginComponent from './component/LoginComponent.jsx';
import LogoutComponent from './component/LogoutComponent.jsx';
import SignupComponent from './component/SignupComponent.jsx';
import AuthenticatedRoute from './component/AuthenticatedRoute.jsx';
import AuthenticationService from './service/AuthenticationService';

function Nav(props){
  const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

  return (
    <nav className="mb-3 mt-2">
      <ul className="list-group list-group-horizontal-sm">
        <li className="list-group-item">
          <Link to="/">Home</Link>
        </li>
        <li className="list-group-item">
          <Link to="/post">Post</Link>
        </li>
        <li className="list-group-item">
          {!isUserLoggedIn && <Link to="/login">Login</Link>}
          {isUserLoggedIn && <Link to="/logout" onClick={AuthenticationService.logout}>Logout</Link>}
        </li>
        {!isUserLoggedIn && 
          <li className="list-group-item">
            <Link to="/signup">Sign Up</Link>
          </li>
        }
      </ul>
    </nav>
  );
}

function Breadcrumb(props){
  const username = AuthenticationService.getUsername();

  return (
    <>
    {username && 
      <nav aria-label="breadcrumb">
        <ol className="breadcrumb">
          <li className="breadcrumb-item active" aria-current="page">login username : {username}</li>
        </ol>
      </nav>
    }
    </>
  )
}

const NavWithRouter = withRouter(Nav);
const NavBreadcrumb = withRouter(Breadcrumb);

class App extends React.Component {

  render(){
    return (
      <div className="container">
        <Router>
            <NavWithRouter/>
            <NavBreadcrumb/>
            <Switch>
              <Route path="/login" exact component={LoginComponent} />
              <Route path="/signup" exact component={SignupComponent} />
              <AuthenticatedRoute path="/" exact component={Home} />
              <AuthenticatedRoute path="/logout" exact component={LogoutComponent} />
              <AuthenticatedRoute path="/post" exact component={PostController} />
            </Switch>
        </Router>
      </div>
    );
  }
  
}

export default App;