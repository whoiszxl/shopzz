import React from 'react';
import ReactDOM from 'react-dom';
import {HashRouter as Router, Route} from 'react-router-dom';

import 'font-awesome/css/font-awesome.min.css';
import './index.scss';

class A extends React.Component{
    constructor(){
        super();
    }
    render(){
        return <div>Component A</div>
    }
}

class B extends React.Component{
    constructor(){
        super();
    }
    render(){
        return <div>Component B</div>
    }
}

class Container extends React.Component{
    constructor(){
        super();
    }
    render(){
        return (
            <div>
                {this.props.children}
            </div>
        );
    }
}

ReactDOM.render(
    <Router>
        <Container>
            <A></A>
            <B></B>
        </Container>
    </Router>,
    document.getElementById('app')
);