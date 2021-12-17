
Object.extend(String.prototype, {
    trim: function () {
        return this.replace(/(^[\s　]*)|([\s　]*$)/g, "");
    },

    lTrim: function () {
        return this.replace(/(^[\s　]*)/g, "");
    },

    rTrim: function () {
        return this.replace(/([\s　]*$)/g, "");
    },

    bytelength: function () {
        var doubleByteChars = this.match(/[^\x00-\xff]/ig);
        return this.length + (doubleByteChars == null ? 0 : doubleByteChars.length);
    },

    cut: function (n) {
        if (n > this.length) {
            return this;
        }
        return this.substring(0, n);
    },

    formatWithWBR: function () {
        var args = arguments, size = 10;
        if (args.length > 0) {
            var argument = parseInt(args[0]);
            if (!isNaN(argument) && argument > 0) {
                size = argument;
            }
        }
        var text = this, output = [], start = 0, rowStart = 0, nextChar;
        for (var i = 1, count = text.length; i < count; i++) {
            nextChar = text.charAt(i);
            if (/\s/.test(nextChar)) {
                rowStart = i;
            } else {
                if ((i - rowStart) == size) {
                    output.push(text.substring(start, i));
                    output.push("<wbr>");
                    start = rowStart = i;
                }
            }
        }
        output.push(text.substr(start));
        return output.join("");
    },

    gsub: function (pattern, replacement) {
        var result = '', source = this, match;
        replacement = arguments.callee.prepareReplacement(replacement);

        while (source.length > 0) {
            if (match = source.match(pattern)) {
                result += source.slice(0, match.index);
                result += String.interpret(replacement(match));
                source = source.slice(match.index + match[0].length);
            } else {
                result += source, source = '';
            }
        }
        return result;
    },

    sub: function (pattern, replacement, count) {
        replacement = this.gsub.prepareReplacement(replacement);
        count = count === undefined ? 1 : count;

        return this.gsub(pattern, function (match) {
            if (--count < 0) return match[0];
            return replacement(match);
        });
    },

    scan: function (pattern, iterator) {
        this.gsub(pattern, iterator);
        return this;
    },

    truncate: function (length, truncation) {
        length = length || 30;
        truncation = truncation === undefined ? '...' : truncation;
        return this.length > length ?
        this.slice(0, length - truncation.length) + truncation : this;
    },

    strip: function () {
        return this.replace(/^\s+/, '').replace(/\s+$/, '');
    },

    stripTags: function () {
        return this.replace(/<\/?[^>]+>/gi, '');
    },

    stripScripts: function () {
        return this.replace(new RegExp(Prototype.ScriptFragment, 'img'), '');
    },

    extractScripts: function () {
        var matchAll = new RegExp(Prototype.ScriptFragment, 'img');
        var matchOne = new RegExp(Prototype.ScriptFragment, 'im');
        return (this.match(matchAll) || []).map(function (scriptTag) {
            return (scriptTag.match(matchOne) || ['', ''])[1];
        });
    },

    evalScripts: function () {
        return this.extractScripts().map(function (script) { return eval(script) });
    },

    escapeHTML: function () {
        var self = arguments.callee;
        self.text.data = this;
        return self.div.innerHTML;
    },

    unescapeHTML: function () {
        var div = document.createElement('div');
        div.innerHTML = this.stripTags();
        return div.childNodes[0] ? (div.childNodes.length > 1 ?
            $A(div.childNodes).inject('', function (memo, node) { return memo + node.nodeValue }) :
            div.childNodes[0].nodeValue) : '';
    },

    toQueryParams: function (separator) {
        var match = this.strip().match(/([^?#]*)(#.*)?$/);
        if (!match) return {};

        return match[1].split(separator || '&').inject({}, function (hash, pair) {
            if ((pair = pair.split('='))[0]) {
                var key = decodeURIComponent(pair.shift());
                var value = pair.length > 1 ? pair.join('=') : pair[0];
                if (value != undefined) value = decodeURIComponent(value);

                if (key in hash) {
                    if (hash[key].constructor != Array) hash[key] = [hash[key]];
                    hash[key].push(value);
                }
                else hash[key] = value;
            }
            return hash;
        });
    },

    toArray: function () {
        return this.split('');
    },

    succ: function () {
        return this.slice(0, this.length - 1) +
            String.fromCharCode(this.charCodeAt(this.length - 1) + 1);
    },

    times: function (count) {
        var result = '';
        for (var i = 0; i < count; i++) result += this;
        return result;
    },

    camelize: function () {
        var parts = this.split('-'), len = parts.length;
        if (len == 1) return parts[0];

        var camelized = this.charAt(0) == '-'
            ? parts[0].charAt(0).toUpperCase() + parts[0].substring(1)
            : parts[0];

        for (var i = 1; i < len; i++)
            camelized += parts[i].charAt(0).toUpperCase() + parts[i].substring(1);

        return camelized;
    },

    capitalize: function () {
        return this.charAt(0).toUpperCase() + this.substring(1).toLowerCase();
    },

    underscore: function () {
        return this.gsub(/::/, '/').gsub(/([A-Z]+)([A-Z][a-z])/, '#{1}_#{2}').gsub(/([a-z\d])([A-Z])/, '#{1}_#{2}').gsub(/-/, '_').toLowerCase();
    },

    dasherize: function () {
        return this.gsub(/_/, '-');
    },

    inspect: function (useDoubleQuotes) {
        var escapedString = this.gsub(/[\x00-\x1f\\]/, function (match) {
            var character = String.specialChar[match[0]];
            return character ? character : '\\u00' + match[0].charCodeAt().toPaddedString(2, 16);
        });
        if (useDoubleQuotes) return '"' + escapedString.replace(/"/g, '\\"') + '"';
        return "'" + escapedString.replace(/'/g, '\\\'') + "'";
    },

    toJSON: function () {
        return this.inspect(true);
    },

    unfilterJSON: function (filter) {
        return this.sub(filter || Prototype.JSONFilter, '#{1}');
    },

    isJSON: function () {
        var str = this.replace(/\\./g, '@').replace(/"[^"\\\n\r]*"/g, '');
        return (/^[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]*$/).test(str);
    },

    evalJSON: function (sanitize) {
        var json = this.unfilterJSON();
        try {
            if (!sanitize || json.isJSON()) return eval('(' + json + ')');
        } catch (e) { }
        throw new SyntaxError('Badly formed JSON string: ' + this.inspect());
    },

    include: function (pattern) {
        return this.indexOf(pattern) > -1;
    },

    startsWith: function (pattern) {
        return this.indexOf(pattern) === 0;
    },

    endsWith: function (pattern) {
        var d = this.length - pattern.length;
        return d >= 0 && this.lastIndexOf(pattern) === d;
    },

    empty: function () {
        return this.trim() == '';
    },

    blank: function () {
        return /^\s*$/.test(this);
    }
});

String.isNullOrEmpty = function (str) {
    return str == undefined || str == null || str.empty();
};

String.format = function () {
    var args = arguments, argsCount = args.length;
    if (argsCount == 0) {
        return "";
    }
    if (argsCount == 1) {
        return args[0];
    }
    var reg = /{(\d+)?}/g, arg, result;
    if (args[1] instanceof Array) {
        arg = args[1];
        result = args[0].replace(reg, function ($0, $1) {
            return arg[parseInt($1)];
        });
    } else {
        arg = args;
        result = args[0].replace(reg, function ($0, $1) {
            return arg[parseInt($1) + 1];
        });
    }
    return result;
};