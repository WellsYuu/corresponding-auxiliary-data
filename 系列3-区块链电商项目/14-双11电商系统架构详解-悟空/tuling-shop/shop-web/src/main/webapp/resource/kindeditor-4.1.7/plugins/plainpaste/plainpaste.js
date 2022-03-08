/*
 * Copyright 2021-2022 the original author or authors
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

KindEditor.plugin('plainpaste', function(K) {
	var self = this, name = 'plainpaste';
	self.clickToolbar(name, function() {
		var lang = self.lang(name + '.'),
			html = '<div style="padding:10px 20px;">' +
				'<div style="margin-bottom:10px;">' + lang.comment + '</div>' +
				'<textarea class="ke-textarea" style="width:408px;height:260px;"></textarea>' +
				'</div>',
			dialog = self.createDialog({
				name : name,
				width : 450,
				title : self.lang(name),
				body : html,
				yesBtn : {
					name : self.lang('yes'),
					click : function(e) {
						var html = textarea.val();
						html = K.escape(html);
						html = html.replace(/ {2}/g, ' &nbsp;');
						if (self.newlineTag == 'p') {
							html = html.replace(/^/, '<p>').replace(/$/, '</p>').replace(/\n/g, '</p><p>');
						} else {
							html = html.replace(/\n/g, '<br />$&');
						}
						self.insertHtml(html).hideDialog().focus();
					}
				}
			}),
			textarea = K('textarea', dialog.div);
		textarea[0].focus();
	});
});
