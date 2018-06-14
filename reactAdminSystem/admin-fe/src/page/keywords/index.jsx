/*
* @Author: whoiszxl
* @Date:   2018-01-26 16:48:16
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-13 16:38:10
*/
import React        from 'react';
import { Link }     from 'react-router-dom';
import MUtil        from 'util/mm.jsx'
import Article      from 'service/article-service.jsx'
import FileUploader from 'util/file-uploader/index.jsx'

import PageTitle    from 'component/page-title/index.jsx';
import TableList    from 'util/table-list/index.jsx';
import Pagination   from 'util/pagination/index.jsx';

import "./index.scss";

const _mm   = new MUtil();
const _article = new Article();

class KeywordsList extends React.Component{
    constructor(props){
        
        super(props);
        this.state = {
            list            : []
        };
    }
    componentDidMount(){
        this.loadKeywordsList();
    }
    loadKeywordsList(){
        _article.getKeywordsList().then(res => {
            this.setState({
              list : res
            });
        }, errMsg => {
            this.setState({
                list : []
            });
            _mm.errorTips(errMsg);
        });
    }

    // 更新首页关键词
    onUpdateKeywords(keywordsId, keywordsWords){
        let word = window.prompt('请输入需要修改的关键词，用英文状态下的逗号(,)隔开', keywordsWords);
        if(word){

            _article.updateKeywordsWords({
                id: keywordsId,
                words : word
            }).then(res => {
                _mm.successTips(res);
                this.loadKeywordsList();
            }, errMsg => {
                _mm.errorTips(errMsg);
            });
        }
    }

    render(){
        let listBody = this.state.list.map((keywords, index) => {
            return (
                <tr key={index}>
                    <td>{keywords.id}</td>
                    <td>{keywords.words}</td>
                    <td>
                        <Link to="#" className="opear" onClick={(e) => this.onUpdateKeywords(keywords.id, keywords.words) }>修改首页关键词</Link>
                    </td>
                </tr>
            );
        });
        return (
            <div id="page-wrapper">
                <PageTitle title="轮播图列表"/>
                <TableList tableHeads={['ID', '标题', '操作']}>
                    {listBody}
                </TableList>
            </div>
        );
    }
}

export default KeywordsList;