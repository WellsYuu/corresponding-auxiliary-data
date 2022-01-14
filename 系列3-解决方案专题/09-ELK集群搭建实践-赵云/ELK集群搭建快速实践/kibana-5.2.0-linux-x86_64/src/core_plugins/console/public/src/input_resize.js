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

const $ = require('jquery');
const storage = require('./storage');

module.exports = function (input, output) {

  const $left = input.$el.parent();

  function readStoredEditorWidth() {
    return storage.get('editorWidth');
  }

  function storeEditorWidth(editorWidth) {
    storage.set('editorWidth', editorWidth);
  }

  function setEditorWidth(editorWidth) {
    storeEditorWidth(editorWidth);
    $left.width(editorWidth);
  }

  var $resizer = $('#editor_resizer');
  $resizer
    .on('mousedown', function (event) {
      $resizer.addClass('active');
      var startWidth = $left.width();
      var startX = event.pageX;

      function onMove(event) {
        setEditorWidth(startWidth + event.pageX - startX)
      }

      $(document.body)
        .on('mousemove', onMove)
        .one('mouseup', function () {
          $resizer.removeClass('active');
          $(this).off('mousemove', onMove);
          input.resize();
          output.resize();
        });
    });

  const initialEditorWidth = readStoredEditorWidth();
  if (initialEditorWidth != null) {
    setEditorWidth(initialEditorWidth);
  }

}
