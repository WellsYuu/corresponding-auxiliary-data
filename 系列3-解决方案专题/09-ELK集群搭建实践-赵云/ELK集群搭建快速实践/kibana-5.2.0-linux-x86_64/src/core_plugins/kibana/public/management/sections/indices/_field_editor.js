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

import 'ui/field_editor';
import 'plugins/kibana/management/sections/indices/_index_header';
import IndexPatternsFieldProvider from 'ui/index_patterns/_field';
import UrlProvider from 'ui/url';
import uiRoutes from 'ui/routes';
import fieldEditorTemplate from 'plugins/kibana/management/sections/indices/_field_editor.html';

uiRoutes
.when('/management/kibana/indices/:indexPatternId/field/:fieldName*', { mode: 'edit' })
.when('/management/kibana/indices/:indexPatternId/create-field/', { mode: 'create' })
.defaults(/management\/kibana\/indices\/[^\/]+\/(field|create-field)(\/|$)/, {
  template: fieldEditorTemplate,
  resolve: {
    indexPattern: function ($route, courier) {
      return courier.indexPatterns.get($route.current.params.indexPatternId)
      .catch(courier.redirectWhenMissing('/management/kibana/indices'));
    }
  },
  controllerAs: 'fieldSettings',
  controller: function FieldEditorPageController($route, Private, Notifier, docTitle) {
    const Field = Private(IndexPatternsFieldProvider);
    const notify = new Notifier({ location: 'Field Editor' });
    const kbnUrl = Private(UrlProvider);

    this.mode = $route.current.mode;
    this.indexPattern = $route.current.locals.indexPattern;


    if (this.mode === 'edit') {
      const fieldName = $route.current.params.fieldName;
      this.field = this.indexPattern.fields.byName[fieldName];

      if (!this.field) {
        notify.error(this.indexPattern + ' does not have a "' + fieldName + '" field.');
        kbnUrl.redirectToRoute(this.indexPattern, 'edit');
        return;
      }

    }
    else if (this.mode === 'create') {
      this.field = new Field(this.indexPattern, {
        scripted: true,
        type: 'number'
      });
    }
    else {
      throw new Error('unknown fieldSettings mode ' + this.mode);
    }

    docTitle.change([this.field.name || 'New Scripted Field', this.indexPattern.id]);
    this.goBack = function () {
      kbnUrl.changeToRoute(this.indexPattern, 'edit');
    };
  }
});
