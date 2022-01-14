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

import 'plugins/kibana/visualize/saved_visualizations/_saved_vis';
import RegistryVisTypesProvider from 'ui/registry/vis_types';
import uiModules from 'ui/modules';
import { SavedObjectLoader } from 'ui/courier/saved_object/saved_object_loader';

const app = uiModules.get('app/visualize');


// Register this service with the saved object registry so it can be
// edited by the object editor.
require('plugins/kibana/management/saved_object_registry').register({
  service: 'savedVisualizations',
  title: 'visualizations'
});

app.service('savedVisualizations', function (Promise, esAdmin, kbnIndex, SavedVis, Private, Notifier, kbnUrl) {
  const visTypes = Private(RegistryVisTypesProvider);
  const notify = new Notifier({
    location: 'Saved Visualization Service'
  });

  const saveVisualizationLoader = new SavedObjectLoader(SavedVis, kbnIndex, esAdmin, kbnUrl);
  saveVisualizationLoader.mapHits = function (hit) {
    const source = hit._source;
    source.id = hit._id;
    source.url = this.urlFor(hit._id);

    let typeName = source.typeName;
    if (source.visState) {
      try { typeName = JSON.parse(source.visState).type; }
      catch (e) { /* missing typename handled below */ } // eslint-disable-line no-empty
    }

    if (!typeName || !visTypes.byName[typeName]) {
      if (!typeName) notify.error('Visualization type is missing. Please add a type to this visualization.', hit);
      else notify.error('Visualization type of "' + typeName + '" is invalid. Please change to a valid type.', hit);
      return kbnUrl.redirect('/management/kibana/objects/savedVisualizations/{{id}}', { id: source.id });
    }

    source.type = visTypes.byName[typeName];
    source.icon = source.type.icon;
    return source;
  };

  saveVisualizationLoader.urlFor = function (id) {
    return kbnUrl.eval('#/visualize/edit/{{id}}', { id: id });
  };

  return saveVisualizationLoader;
});
