/*
* @Author: whoiszxl
* @Date:   2018-01-26 16:48:16
 * @Last Modified by: whoiszxl
 * @Last Modified time: 2018-06-11 13:04:08
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

class BannerList extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            list            : []
        };
    }
    componentDidMount(){
        this.loadBannerList();
    }
    loadBannerList(){
        _article.getBannerList().then(res => {
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
    // 页数发生变化的时候
    onPageNumChange(pageNum){
        this.setState({
            pageNum : pageNum
        }, () => {
            this.loadUserList();
        });
    }
    render(){
        console.log("aa"+JSON.stringify(this.state[0]));
        let listBody = this.state.list.map((banner, index) => {
            return (
                <tr key={index}>
                    <td>{banner.id}</td>
                    <td>{banner.title}</td>
                    <td>
                        <img className="banner_img" src={banner.imgurl}/>
                    </td>
                    <td>{banner.jumpurl}</td>
                    <td>{banner.sort}</td>
                    <td>{banner.status == 1 ? '有效' : '无效'}</td>
                    <td>{banner.createTime}</td>
                    <td>
                        <Link className="opear" to={ `/banner/save/${banner.id}` }>编辑</Link>
                    </td>
                </tr>
            );
        });
        return (
            <div id="page-wrapper">
                <PageTitle title="轮播图列表"/>
                <TableList tableHeads={['ID', '标题', '图片', '跳转地址', '排序', '状态', '创建时间']}>
                    {listBody}
                </TableList>
            </div>
        );
    }
}

export default BannerList;