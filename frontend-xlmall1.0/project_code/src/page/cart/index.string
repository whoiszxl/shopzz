{{#notEmpty}}
<div class="cart-header">
    <table class="cart-table">
        <tr>
            <th class="cart-cell cell-check">
                <label class="cart-label">
                    {{#allChecked}}
                    <input type="checkbox" class="cart-select-all" checked/>
                    {{/allChecked}}
                    {{^allChecked}}
                    <input type="checkbox" class="cart-select-all" />
                    {{/allChecked}}
                    <span>全选</span>
                </label>
            </th>
            <th class="cart-cell cell-info">商品信息</th>
            <th class="cart-cell cell-price">单价</th>
            <th class="cart-cell cell-count">数量</th>
            <th class="cart-cell cell-total">合计</th>
            <th class="cart-cell cell-opera">操作</th>
        </tr>
    </table>
</div>
<div class="cart-list">
    {{#cartProductVoList}}
    <table class="cart-table" data-product-id="{{productId}}">
        <tr>
            <td class="cart-cell cell-check">
                <label class="cart-label">
                    {{#productChecked}}
                    <input type="checkbox" class="cart-select" checked/>
                    {{/productChecked}}
                    {{^productChecked}}
                    <input type="checkbox" class="cart-select" />
                    {{/productChecked}}
                </label>
            </td>
            <td class="cart-cell cell-img">
                <a class="link" href="./detail.html?productId={{productId}}">
                    <img class="p-img" src="{{imageHost}}{{productMainImage}}" alt="{{productName}}" />
                </a>
            </td>
            <td class="cart-cell cell-info">
                <a class="link" href="./detail.html?productId={{productId}}">{{productName}}</a>
            </td>
            <td class="cart-cell cell-price">￥{{productPrice}}</td>
            <td class="cart-cell cell-count">
                <span class="count-btn minus">-</span>
                <input class="count-input" value="{{quantity}}" data-max="{{productStock}}"/>  
                <span class="count-btn plus">+</span>
            </td>
            <td class="cart-cell cell-total">￥{{productTotalPrice}}</td>
            <td class="cart-cell cell-opera">
                <span class="link cart-delete">删除</span>
            </td>
        </tr>
    </table>
    {{/cartProductVoList}}
</div>
<div class="cart-footer">
    <div class="select-con">
        <label>
            {{#allChecked}}
            <input type="checkbox" class="cart-select-all" checked/>
            {{/allChecked}}
            {{^allChecked}}
            <input type="checkbox" class="cart-select-all" />
            {{/allChecked}}
            <span>全选</span>
        </label>
    </div>
    <div class="delete-con">
        <span class="link delete-selected">
            <i class="fa fa-trash-o"></i>
            <span>删除选中</span>
        </span>
    </div>
    <div class="submit-con">
        <span>总价：</span>
        <span class="submit-total">￥{{cartTotalPrice}}</span>
        <span class="btn btn-submit">去结算</span>
    </div>
</div>
{{/notEmpty}}
{{^notEmpty}}
<p class="err-tip">
    <span>您的购物车空空如也，</span>
    <a href="./index.html">立即去购物</a>
</p>
{{/notEmpty}}