(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2c0f94ee"],{"0bb4":function(t,e,i){"use strict";i.d(e,"e",(function(){return a})),i.d(e,"c",(function(){return s})),i.d(e,"d",(function(){return r})),i.d(e,"a",(function(){return c})),i.d(e,"f",(function(){return l})),i.d(e,"b",(function(){return o}));var n=i("365c");function a(){return n["a"].get("api/notice/totalCount")}function s(t){return n["a"].get("api/notice/all/".concat(t))}function r(t){return n["a"].get("api/notice/detail/".concat(t))}function c(t){return n["b"].post("api/admin/notice/new",t)}function l(t,e){return n["b"].put("api/admin/notice/update/".concat(t),e)}function o(t){return n["b"].delete("api/admin/notice/delete",{data:t})}},"0c24":function(t,e,i){"use strict";var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"file-wrapper"},[i("div",{staticClass:"preview-container"},[t.files[0]?i("div",{staticClass:"file-preview-wrapper"},[i("label",{attrs:{for:t.refName}},[i("img",{attrs:{src:t.files[0].preview}})]),i("div",{staticClass:"img-info"},[i("span",[t._v(t._s(t.files[0].file.name))]),i("div",{on:{click:t.fileDeleteButton}},[i("Close",{attrs:{name:t.files[0].number}})],1)])]):i("div",{staticClass:"file-upload"},[i("div",{staticClass:"image-box"},[i("label",{attrs:{for:t.refName}},[i("ImagePlus")],1),i("input",{ref:"files",attrs:{type:"file",id:t.refName},on:{change:t.imageAddUpload}})])])])])},a=[],s=(i("4de4"),i("b0c0"),i("a9e3"),i("d3b7"),i("3ca3"),i("ddb0"),i("2b3d"),i("f2c6")),r=i("fa4d"),c={components:{ImagePlus:s["a"],Close:r["a"]},props:{refName:{type:String},image:{type:Object}},created:function(){console.log(this.refName),this.image&&this.image.imgName&&(this.image.name=this.image.imgName,this.files=[{file:this.image,preview:this.image.imgPath,number:1}])},data:function(){return{files:[],filesPreview:[],uploadImageIndex:0}},methods:{imageAddUpload:function(){this.files=[{file:this.$refs.files.files[0],preview:URL.createObjectURL(this.$refs.files.files[0]),number:1}],console.log(this.refName),this.$emit(this.refName,this.files[0].file)},fileDeleteButton:function(t){this.$emit(this.refName,{});var e=t.target.getAttribute("name");this.files=this.files.filter((function(t){return t.number!==Number(e)}))}}},l=c,o=(i("8535"),i("2877")),u=Object(o["a"])(l,n,a,!1,null,"1116737c",null);e["a"]=u.exports},"775b":function(t,e,i){"use strict";i.r(e);var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"sub-container"},[i("ul",{staticClass:"tabs sub"},[i("li",{staticClass:"current",on:{click:function(e){return t.goto("/admin/notice")}}},[t._v("공지")]),i("li",{on:{click:function(e){return t.goto("/admin/news")}}},[t._v("뉴스")])]),i("div",{staticClass:"contents-container"},[i("p",{staticClass:"contents-title underline marB-20 body3"},[t._v(" 새 공지 등록 ")]),i("div",{staticClass:"input-container"},[i("div",{staticClass:"img-upload-container"},[i("div",{staticClass:"input-item align-top"},[i("input",{attrs:{type:"file",id:"files",name:"file"}}),i("AddImageSingle",{attrs:{refName:"mainImage"},on:{mainImage:t.setMainImage}})],1)]),i("div",{staticClass:"input-item"},[i("p",{staticClass:"small-title"},[t._v("제목")]),i("input",{directives:[{name:"model",rawName:"v-model",value:t.title,expression:"title"}],staticClass:"admin-input large",attrs:{type:"text",name:"title",value:"프로그램명"},domProps:{value:t.title},on:{input:function(e){e.target.composing||(t.title=e.target.value)}}})]),i("div",{staticClass:"input-item"},[i("p",{staticClass:"small-title"},[t._v("내용")]),i("CKEditor",{ref:"editor",attrs:{contents:t.contents}})],1),i("button",{staticClass:"outlined-btn",on:{click:t.addNotice}},[t._v("등록")])])])])},a=[],s=(i("96cf"),i("1da1")),r=i("5530"),c=i("4360"),l=i("0c24"),o=i("8698"),u=i("2f62"),f=i("0bb4"),m={components:{AddImageSingle:l["a"],CKEditor:o["a"]},computed:Object(r["a"])({},Object(u["b"])(["main"])),created:function(){c["a"].commit("SET_MAIN",null)},data:function(){return{title:"",contents:""}},methods:{goto:function(t){this.$router.push({path:t})},addNotice:function(){var t=this;return Object(s["a"])(regeneratorRuntime.mark((function e(){var i,n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(t.contents=t.$refs.editor.getContents(),t.title){e.next=5;break}return t.errMsg="제목을 입력해주세요",alert(t.errMsg),e.abrupt("return",!1);case 5:if(t.contents){e.next=9;break}return t.errMsg="콘텐츠를 입력해주세요",alert(t.errMsg),e.abrupt("return",!1);case 9:return i=new FormData,t.main&&i.append("file",t.main),i.append("title",t.title),i.append("contents",t.contents),e.prev=13,e.next=16,Object(f["a"])(i);case 16:if(n=e.sent,console.log(n),200!=n.status){e.next=24;break}return alert("공지사항이 등록되었습니다"),e.next=22,t.initForm();case 22:e.next=25;break;case 24:alert("에러가 발생했습니다");case 25:e.next=30;break;case 27:e.prev=27,e.t0=e["catch"](13),alert("에러가 발생했습니다");case 30:case"end":return e.stop()}}),e,null,[[13,27]])})))()},initForm:function(){this.selected="",this.title="",this.contents="",c["a"].commit("SET_MAIN","")},setMainImage:function(t){c["a"].commit("SET_MAIN",t)}}},d=m,p=i("2877"),g=Object(p["a"])(d,n,a,!1,null,null,null);e["default"]=g.exports},8535:function(t,e,i){"use strict";i("d681")},d681:function(t,e,i){}}]);
//# sourceMappingURL=chunk-2c0f94ee.ef83ab99.js.map