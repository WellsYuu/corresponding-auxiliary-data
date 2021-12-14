<%--
  ~ Copyright [$tody.year] [Wales Yu of copyright owner]
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

@/*
    按钮标签中各个参数的说明:

    btnType : 按钮的类型决定了颜色(default-灰色,primary-绿色,success-蓝色,info-淡蓝色,warning-黄色,danger-红色,white-白色)
    space : 按钮左侧是否有间隔(true/false)
    clickFun : 点击按钮所执行的方法
    icon : 按钮上的图标的样式
    name : 按钮名称
@*/

@var spaceCss = "";
@var btnType = "";
@if(isEmpty(space)){
@   spaceCss = "";
@}else{
@   spaceCss = "button-margin";
@}
@if(isEmpty(btnCss)){
@   btnType = "primary";
@}else{
@   btnType = btnCss;
@}
<button type="button" class="btn btn-${btnType} ${spaceCss}" onclick="${clickFun!}" id="${id!}">
    <i class="fa ${icon}"></i>&nbsp;${name}
</button>

