<div class="intro-wrap">
    <div class="p-img-con">
        <div class="main-img-con">
            <img class="main-img" src="{{imageHost}}{{mainImage}}" alt="{{name}}"/>
        </div>
        <ul class="p-img-list">
            {{#subImages}}
            <li class="p-img-item">
                <img class="p-img" src="{{imageHost}}{{.}}" alt="{{name}}" />
            </li>
            {{/subImages}}
        </ul>
    </div>
    <div class="p-info-con">
        <h1 class="p-name">{{name}}</h1>
        <p class="p-subtitle">{{subtitle}}</p>
        <div class="p-info-item p-price-con">
            <span class="label">价格:</span>
            <span class="info">￥{{price}}</span>
        </div>
        <div class="p-info-item">
            <span class="label">库存:</span>
            <span class="info">{{stock}}</span>
        </div>
        <div class="p-info-item p-count-con">
            <span class="label">数量:</span>
            <input class="p-count" value="1" readonly=""/>
            <span class="p-count-btn plus">+</span>
            <span class="p-count-btn minus">-</span>
        </div>
        <div class="p-info-item">
            <a class="btn cart-add">加入购物车</a>
        </div>
    </div>
</div>
<div class="detail-wrap">
    <div class="detail-tab-con">
        <ul class="tab-list">
            <li class="tab-item active">详细描述</li>
        </ul>
    </div>
    <div class="detail-con">
        {{{detail}}}
    </div>
</div>