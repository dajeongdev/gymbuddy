(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3323b828"],{"94f1":function(t,e,n){"use strict";n("f07d")},b46e:function(t,e,n){"use strict";n.d(e,"c",(function(){return a})),n.d(e,"d",(function(){return o})),n.d(e,"e",(function(){return c})),n.d(e,"a",(function(){return s})),n.d(e,"g",(function(){return u})),n.d(e,"b",(function(){return l})),n.d(e,"f",(function(){return r}));var i=n("365c");function r(){return i["a"].get("api/frequencyQuestion/totalCount")}function a(t){return i["b"].get("api/admin/frequencyQuestion/all/".concat(t))}function c(t){return i["a"].get("api/frequencyQuestion/detail/".concat(t))}function o(){return i["a"].get("api/frequencyQuestion/all")}function s(t){return i["b"].post("api/admin/frequencyQuestion/new",t)}function u(t,e){return i["b"].put("api/admin/frequencyQuestion/update/".concat(t),e)}function l(t){return i["b"].put("api/admin/frequencyQuestion/delete",t)}},c96f:function(t,e,n){"use strict";n.r(e);var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"sub-container faq"},[t._m(0),n("Accordian",{attrs:{id:"faq",content:t.faqList,multiple:!0}})],1)},r=[function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"page-title center"},[n("h2",[t._v("FAQ")])])}],a=(n("d81d"),n("96cf"),n("1da1")),c=n("b46e"),o=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("dl",{staticClass:"accordion box",attrs:{role:"presentation"}},t._l(t.content,(function(e){return n("AccordianItem",{key:e.id,attrs:{multiple:t.multiple,item:e,groupId:t.groupId}})})),1)},s=[],u=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"accordion-item",class:{"is-active":t.item.active},attrs:{id:t.groupId+"-"+t.item.id}},[n("dt",{staticClass:"accordion-item-title"},[n("button",{staticClass:"accordion-item-trigger",on:{click:t.toggle}},[n("div",{staticClass:"accordion-category"},[t._v(t._s(t.item.category))]),n("h4",{staticClass:"accordion-item-title-text"},[t._v(t._s(t.item.title))])])]),n("transition",{attrs:{name:"accordion-item"},on:{enter:t.startTransition,"after-enter":t.endTransition,"before-leave":t.startTransition,"after-leave":t.endTransition}},[t.item.active?n("dd",{staticClass:"accordion-item-details"},[n("div",{staticClass:"accordion-item-details-inner",domProps:{innerHTML:t._s(t.item.contents)}})]):t._e()])],1)},l=[],d=(n("4160"),n("159b"),{props:["item","multiple","groupId"],methods:{toggle:function(t){this.multiple?(console.log(this.item.active),this.item.active=!this.item.active):(console.log(this.$parent.$children),this.$parent.$children.forEach((function(e){e.$el.id===t.currentTarget.parentElement.parentElement.id?e.item.active=!e.item.active:e.item.active=!1})))},startTransition:function(t){t.style.height=t.scrollHeight+"px"},endTransition:function(t){t.style.height=""}}}),f=d,p=n("2877"),m=Object(p["a"])(f,u,l,!1,null,null,null),g=m.exports,h={components:{AccordianItem:g},mounted:function(){this.groupId=this.$el.id},props:{content:{type:Array,required:!0},multiple:{type:Boolean,default:!1}},data:function(){return{groupId:null}}},v=h,y=Object(p["a"])(v,o,s,!1,null,null,null),b=y.exports,_={components:{Accordian:b},created:function(){this.fetchFaq()},mounted:function(){},data:function(){return{faqList:[],isLoading:!1}},methods:{fetchFaq:function(){var t=this;return Object(a["a"])(regeneratorRuntime.mark((function e(){var n,i;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return t.isLoading=!0,e.prev=1,e.next=4,Object(c["d"])(0);case 4:n=e.sent,i=n.data,t.faqList=i.map((function(t){return"AC"==t.categoryId?t.category="계정":"PY"==t.categoryId?t.category="결제":"SH"==t.categoryId?t.category="배송":"PD"==t.categoryId?t.category="프로그램 및 굿즈":"HP"==t.categoryId?t.category="홈페이지 이용":"ETC"==t.categoryId&&(t.category="기타"),t.active=!1,t})),console.log(t.faqList),e.next=13;break;case 10:e.prev=10,e.t0=e["catch"](1),console.log(e.t0);case 13:return e.prev=13,t.isLoading=!1,e.finish(13);case 16:case"end":return e.stop()}}),e,null,[[1,10,13,16]])})))()}}},q=_,I=(n("94f1"),Object(p["a"])(q,i,r,!1,null,null,null));e["default"]=I.exports},f07d:function(t,e,n){}}]);
//# sourceMappingURL=chunk-3323b828.b8db82a7.js.map