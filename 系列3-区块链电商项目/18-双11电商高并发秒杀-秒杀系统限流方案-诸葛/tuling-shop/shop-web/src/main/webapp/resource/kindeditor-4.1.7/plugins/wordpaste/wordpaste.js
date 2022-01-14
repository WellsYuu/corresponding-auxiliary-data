/*
 * Copyright 2021-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

KindEditor.plugin('wordpaste', function(K) {
	var self = this, name = 'wordpaste';
	self.clickToolbar(name, function() {
		var lang = self.lang(name + '.'),
			html = '<div style="padding:10px 20px;">' +
				'<div style="margin-bottom:10px;">' + lang.comment + '</div>' +
				'<iframe class="ke-textarea" frameborder="0" style="width:408px;height:260px;"></iframe>' +
				'</div>',
			dialog = self.createDialog({
				name : name,
				width : 450,
				title : self.lang(name),
				body : html,
				yesBtn : {
					name : self.lang('yes'),
					click : function(e) {
						var str = doc.body.innerHTML;
						str = K.clearMsWord(str, self.filterMode ? self.htmlTags : K.options.htmlTags);
						self.insertHtml(str).hideDialog().focus();
					}
				}
			}),
			div = dialog.div,
			iframe = K('iframe', div),
			doc = K.iframeDoc(iframe);
		if (!K.IE) {
			doc.designMode = 'on';
		}
		doc.open();
		doc.write('<!doctype html><html><head><title>WordPaste</title></head>');
		doc.write('<body style="background-color:#FFF;font-size:12px;margin:2px;">');
		if (!K.IE) {
			doc.write('<br />');
		}
		doc.write('</body></html>');
		doc.close();
		if (K.IE) {
			doc.body.contentEditable = 'true';
		}
		iframe[0].contentWindow.focus();
	});
});
