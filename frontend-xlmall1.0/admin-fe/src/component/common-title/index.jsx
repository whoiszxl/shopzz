import React from 'react';

class CommonTitle extends React.Component {
    constructor(){
        super();
    }
    componentWillMount(){
        document.title = this.props.title + ' -- HAPPY MMALL';
    }
    render() {
        return (
            <div className="row">
                <div className="col-md-12">
                    <h1 className="page-header">
                        {this.props.title}
                    </h1>
                    {this.props.children}
                </div>
            </div>
        );
    }
}

export default CommonTitle;