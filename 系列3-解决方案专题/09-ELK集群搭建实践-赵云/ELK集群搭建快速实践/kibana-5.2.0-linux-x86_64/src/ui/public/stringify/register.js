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

import fieldFormats from 'ui/registry/field_formats';
import stringifyUrl from 'ui/stringify/types/url';
import stringifyBytes from 'ui/stringify/types/bytes';
import stringifyDate from 'ui/stringify/types/date';
import stringifyDuration from 'ui/stringify/types/duration';
import stringifyIp from 'ui/stringify/types/ip';
import stringifyNumber from 'ui/stringify/types/number';
import stringifyPercent from 'ui/stringify/types/percent';
import stringifyString from 'ui/stringify/types/string';
import stringifySource from 'ui/stringify/types/source';
import stringifyColor from 'ui/stringify/types/color';
import stringifyTruncate from 'ui/stringify/types/truncate';
import stringifyBoolean from 'ui/stringify/types/boolean';

fieldFormats.register(stringifyUrl);
fieldFormats.register(stringifyBytes);
fieldFormats.register(stringifyDate);
fieldFormats.register(stringifyDuration);
fieldFormats.register(stringifyIp);
fieldFormats.register(stringifyNumber);
fieldFormats.register(stringifyPercent);
fieldFormats.register(stringifyString);
fieldFormats.register(stringifySource);
fieldFormats.register(stringifyColor);
fieldFormats.register(stringifyTruncate);
fieldFormats.register(stringifyBoolean);
