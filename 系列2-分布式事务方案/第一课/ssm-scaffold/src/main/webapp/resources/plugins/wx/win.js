/* ===========================================================
 * bootstrap-modalmanager.js v2.2.0
 * ===========================================================
 * Copyright 2012 Jordan Schroter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */

!function ($) {

    "use strict"; // jshint ;_;

    /* MODAL MANAGER CLASS DEFINITION
     * ====================== */

    var ModalManager = function (element, options) {
        this.init(element, options);
    };

    ModalManager.prototype = {

        constructor: ModalManager,

        init: function (element, options) {
            this.$element = $(element);
            this.options = $.extend({}, $.fn.modalmanager.defaults, this.$element.data(), typeof options == 'object' && options);
            this.stack = [];
            this.backdropCount = 0;

            if (this.options.resize) {
                var resizeTimeout,
                    that = this;

                $(window).on('resize.modal', function(){
                    resizeTimeout && clearTimeout(resizeTimeout);
                    resizeTimeout = setTimeout(function(){
                        for (var i = 0; i < that.stack.length; i++){
                            that.stack[i].isShown && that.stack[i].layout();
                        }
                    }, 10);
                });
            }
        },

        createModal: function (element, options) {
            $(element).modal($.extend({ manager: this }, options));
        },

        appendModal: function (modal) {
            this.stack.push(modal);

            var that = this;

            modal.$element.on('show.modalmanager', targetIsSelf(function (e) {

                var showModal = function(){
                    modal.isShown = true;

                    var transition = $.support.transition && modal.$element.hasClass('fade');

                    that.$element
                        .toggleClass('modal-open', that.hasOpenModal())
                        .toggleClass('page-overflow', $(window).height() < that.$element.height());

                    modal.$parent = modal.$element.parent();

                    modal.$container = that.createContainer(modal);

                    modal.$element.appendTo(modal.$container);

                    that.backdrop(modal, function () {
                        modal.$element.show();

                        if (transition) {
                            //modal.$element[0].style.display = 'run-in';
                            modal.$element[0].offsetWidth;
                            //modal.$element.one($.support.transition.end, function () { modal.$element[0].style.display = 'block' });
                        }

                        modal.layout();

                        modal.$element
                            .addClass('in')
                            .attr('aria-hidden', false);

                        var complete = function () {
                            that.setFocus();
                            modal.$element.trigger('shown');
                        };

                        transition ?
                            modal.$element.one($.support.transition.end, complete) :
                            complete();
                    });
                };

                modal.options.replace ?
                    that.replace(showModal) :
                    showModal();
            }));

            modal.$element.on('hidden.modalmanager', targetIsSelf(function (e) {

                that.backdrop(modal);
                if (modal.$backdrop){
                    var transition = $.support.transition && modal.$element.hasClass('fade');

                    // trigger a relayout due to firebox's buggy transition end event
                    if (transition) { modal.$element[0].offsetWidth; }

                    $.support.transition && modal.$element.hasClass('fade') ?
                        modal.$backdrop.one($.support.transition.end, function () { that.destroyModal(modal) }) :
                        that.destroyModal(modal);
                } else {
                    that.destroyModal(modal);
                }

            }));

            modal.$element.on('destroy.modalmanager', targetIsSelf(function (e) {
                that.removeModal(modal);
            }));

        },

        destroyModal: function (modal) {
            modal.destroy();

            var hasOpenModal = this.hasOpenModal();

            this.$element.toggleClass('modal-open', hasOpenModal);

            if (!hasOpenModal){
                this.$element.removeClass('page-overflow');
            }

            this.removeContainer(modal);

            this.setFocus();
        },

        getOpenModals: function () {
            var openModals = [];
            for (var i = 0; i < this.stack.length; i++){
                if (this.stack[i].isShown) openModals.push(this.stack[i]);
            }

            return openModals;
        },

        hasOpenModal: function () {
            return this.getOpenModals().length > 0;
        },

        setFocus: function () {
            var topModal;

            for (var i = 0; i < this.stack.length; i++){
                if (this.stack[i].isShown) topModal = this.stack[i];
            }

            if (!topModal) return;

            topModal.focus();

        },

        removeModal: function (modal) {
            modal.$element.off('.modalmanager');
            if (modal.$backdrop) this.removeBackdrop(modal);
            this.stack.splice(this.getIndexOfModal(modal), 1);
        },

        getModalAt: function (index) {
            return this.stack[index];
        },

        getIndexOfModal: function (modal) {
            for (var i = 0; i < this.stack.length; i++){
                if (modal === this.stack[i]) return i;
            }
        },

        replace: function (callback) {
            var topModal;

            for (var i = 0; i < this.stack.length; i++){
                if (this.stack[i].isShown) topModal = this.stack[i];
            }

            if (topModal) {
                this.$backdropHandle = topModal.$backdrop;
                topModal.$backdrop = null;

                callback && topModal.$element.one('hidden',
                    targetIsSelf( $.proxy(callback, this) ));

                topModal.hide();
            } else if (callback) {
                callback();
            }
        },

        removeBackdrop: function (modal) {
            modal.$backdrop.remove();
            modal.$backdrop = null;
        },

        createBackdrop: function (animate, tmpl) {
            var $backdrop;

            if (!this.$backdropHandle) {
                $backdrop = $(tmpl)
                    .addClass(animate)
                    .appendTo(this.$element);
            } else {
                $backdrop = this.$backdropHandle;
                $backdrop.off('.modalmanager');
                this.$backdropHandle = null;
                this.isLoading && this.removeSpinner();
            }

            return $backdrop;
        },

        removeContainer: function (modal) {
            modal.$container.remove();
            modal.$container = null;
        },

        createContainer: function (modal) {
            var $container;

            $container = $('<div class="modal-scrollable">')
                .css('z-index', getzIndex('modal', this.getOpenModals().length))
                .appendTo(this.$element);

            if (modal && modal.options.backdrop != 'static') {
                $container.on('click.modal', targetIsSelf(function (e) {
                    modal.hide();
                }));
            } else if (modal) {
                $container.on('click.modal', targetIsSelf(function (e) {
                    modal.attention();
                }));
            }

            return $container;

        },

        backdrop: function (modal, callback) {
            var animate = modal.$element.hasClass('fade') ? 'fade' : '',
                showBackdrop = modal.options.backdrop &&
                    this.backdropCount < this.options.backdropLimit;

            if (modal.isShown && showBackdrop) {
                var doAnimate = $.support.transition && animate && !this.$backdropHandle;

                modal.$backdrop = this.createBackdrop(animate, modal.options.backdropTemplate);

                modal.$backdrop.css('z-index', getzIndex( 'backdrop', this.getOpenModals().length ));

                if (doAnimate) modal.$backdrop[0].offsetWidth; // force reflow

                modal.$backdrop.addClass('in');

                this.backdropCount += 1;

                doAnimate ?
                    modal.$backdrop.one($.support.transition.end, callback) :
                    callback();

            } else if (!modal.isShown && modal.$backdrop) {
                modal.$backdrop.removeClass('in');

                this.backdropCount -= 1;

                var that = this;

                $.support.transition && modal.$element.hasClass('fade')?
                    modal.$backdrop.one($.support.transition.end, function () { that.removeBackdrop(modal) }) :
                    that.removeBackdrop(modal);

            } else if (callback) {
                callback();
            }
        },

        removeSpinner: function(){
            this.$spinner && this.$spinner.remove();
            this.$spinner = null;
            this.isLoading = false;
        },

        removeLoading: function () {
            this.$backdropHandle && this.$backdropHandle.remove();
            this.$backdropHandle = null;
            this.removeSpinner();
        },

        loading: function (callback) {
            callback = callback || function () { };

            this.$element
                .toggleClass('modal-open', !this.isLoading || this.hasOpenModal())
                .toggleClass('page-overflow', $(window).height() < this.$element.height());

            if (!this.isLoading) {

                this.$backdropHandle = this.createBackdrop('fade', this.options.backdropTemplate);

                this.$backdropHandle[0].offsetWidth; // force reflow

                var openModals = this.getOpenModals();

                this.$backdropHandle
                    .css('z-index', getzIndex('backdrop', openModals.length + 1))
                    .addClass('in');

                var $spinner = $(this.options.spinner)
                    .css('z-index', getzIndex('modal', openModals.length + 1))
                    .appendTo(this.$element)
                    .addClass('in');

                this.$spinner = $(this.createContainer())
                    .append($spinner)
                    .on('click.modalmanager', $.proxy(this.loading, this));

                this.isLoading = true;

                $.support.transition ?
                    this.$backdropHandle.one($.support.transition.end, callback) :
                    callback();

            } else if (this.isLoading && this.$backdropHandle) {
                this.$backdropHandle.removeClass('in');

                var that = this;
                $.support.transition ?
                    this.$backdropHandle.one($.support.transition.end, function () { that.removeLoading() }) :
                    that.removeLoading();

            } else if (callback) {
                callback(this.isLoading);
            }
        }
    };

    /* PRIVATE METHODS
     * ======================= */

    // computes and caches the zindexes
    var getzIndex = (function () {
        var zIndexFactor,
            baseIndex = {};

        return function (type, pos) {

            if (typeof zIndexFactor === 'undefined'){
                var $baseModal = $('<div class="modal hide" />').appendTo('body'),
                    $baseBackdrop = $('<div class="modal-backdrop hide" />').appendTo('body');

                baseIndex['modal'] = +$baseModal.css('z-index');
                baseIndex['backdrop'] = +$baseBackdrop.css('z-index');
                zIndexFactor = baseIndex['modal'] - baseIndex['backdrop'];

                $baseModal.remove();
                $baseBackdrop.remove();
                $baseBackdrop = $baseModal = null;
            }

            return baseIndex[type] + (zIndexFactor * pos);

        }
    }());

    // make sure the event target is the modal itself in order to prevent
    // other components such as tabsfrom triggering the modal manager.
    // if Boostsrap namespaced events, this would not be needed.
    function targetIsSelf(callback){
        return function (e) {
            if (this === e.target){
                return callback.apply(this, arguments);
            }
        }
    }


    /* MODAL MANAGER PLUGIN DEFINITION
     * ======================= */

    $.fn.modalmanager = function (option, args) {
        return this.each(function () {
            var $this = $(this),
                data = $this.data('modalmanager');

            if (!data) $this.data('modalmanager', (data = new ModalManager(this, option)));
            if (typeof option === 'string') data[option].apply(data, [].concat(args))
        })
    };

    $.fn.modalmanager.defaults = {
        backdropLimit: 999,
        resize: true,
        spinner: '<div class="loading-spinner fade" style="width: 200px; margin-left: -100px;"><div class="progress progress-striped active"><div class="progress-bar progress-bar-success" style="width: 100%;"></div></div></div>',
        backdropTemplate: '<div class="modal-backdrop" />'
    };

    $.fn.modalmanager.Constructor = ModalManager

}(jQuery);


/* ===========================================================
 * bootstrap-modal.js v2.2.0
 * ===========================================================
 * Copyright 2012 Jordan Schroter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */


!function ($) {

    "use strict"; // jshint ;_;

    /* MODAL CLASS DEFINITION
     * ====================== */

    var Modal = function (element, options) {
        this.init(element, options);
    };

    Modal.prototype = {

        constructor: Modal,

        init: function (element, options) {
            var that = this;

            this.options = options;

            this.$element = $(element)
                .delegate('[data-dismiss="modal"]', 'click.dismiss.modal', $.proxy(this.hide, this));

            this.options.remote && this.$element.find('.modal-body').load(this.options.remote, function () {
                var e = $.Event('loaded');
                that.$element.trigger(e);
            });

            var manager = typeof this.options.manager === 'function' ?
                this.options.manager.call(this) : this.options.manager;

            manager = manager.appendModal ?
                manager : $(manager).modalmanager().data('modalmanager');

            manager.appendModal(this);
        },

        toggle: function () {
            return this[!this.isShown ? 'show' : 'hide']();
        },

        show: function () {
            var e = $.Event('show');

            if (this.isShown) return;

            this.$element.trigger(e);

            if (e.isDefaultPrevented()) return;

            this.escape();

            this.tab();

            this.options.loading && this.loading();
        },

        hide: function (e) {
            e && e.preventDefault();

            e = $.Event('hide');

            this.$element.trigger(e);

            if (!this.isShown || e.isDefaultPrevented()) return (this.isShown = false);

            this.isShown = false;

            this.escape();

            this.tab();

            this.isLoading && this.loading();

            $(document).off('focusin.modal');

            this.$element
                .removeClass('in')
                .removeClass('animated')
                .removeClass(this.options.attentionAnimation)
                .removeClass('modal-overflow')
                .attr('aria-hidden', true);

            $.support.transition && this.$element.hasClass('fade') ?
                this.hideWithTransition() :
                this.hideModal();
        },

        layout: function () {
            var prop = this.options.height ? 'height' : 'max-height',
                value = this.options.height || this.options.maxHeight;

            if (this.options.width){
                this.$element.css('width', this.options.width);

                var that = this;
                this.$element.css('margin-left', function () {
                    if (/%/ig.test(that.options.width)){
                        return -(parseInt(that.options.width) / 2) + '%';
                    } else {
                        return -($(this).width() / 2) + 'px';
                    }
                });
            } else {
                this.$element.css('width', '');
                this.$element.css('margin-left', '');
            }

            this.$element.find('.modal-body')
                .css('overflow', '')
                .css(prop, '');

            if (value){
                this.$element.find('.modal-body')
                    .css('overflow', 'auto')
                    .css(prop, value);
            }

            var modalOverflow = $(window).height() - 10 < this.$element.height();

            if (modalOverflow || this.options.modalOverflow) {
                this.$element
                    .css('margin-top', 0)
                    .addClass('modal-overflow');
            } else {
                this.$element
                    .css('margin-top', 0 - this.$element.height() / 2)
                    .removeClass('modal-overflow');
            }
        },

        tab: function () {
            var that = this;

            if (this.isShown && this.options.consumeTab) {
                this.$element.on('keydown.tabindex.modal', '[data-tabindex]', function (e) {
                    if (e.keyCode && e.keyCode == 9){
                        var $next = $(this),
                            $rollover = $(this);

                        that.$element.find('[data-tabindex]:enabled:not([readonly])').each(function (e) {
                            if (!e.shiftKey){
                                $next = $next.data('tabindex') < $(this).data('tabindex') ?
                                    $next = $(this) :
                                    $rollover = $(this);
                            } else {
                                $next = $next.data('tabindex') > $(this).data('tabindex') ?
                                    $next = $(this) :
                                    $rollover = $(this);
                            }
                        });

                        $next[0] !== $(this)[0] ?
                            $next.focus() : $rollover.focus();

                        e.preventDefault();
                    }
                });
            } else if (!this.isShown) {
                this.$element.off('keydown.tabindex.modal');
            }
        },

        escape: function () {
            var that = this;
            if (this.isShown && this.options.keyboard) {
                if (!this.$element.attr('tabindex')) this.$element.attr('tabindex', -1);

                this.$element.on('keyup.dismiss.modal', function (e) {
                    e.which == 27 && that.hide();
                });
            } else if (!this.isShown) {
                this.$element.off('keyup.dismiss.modal')
            }
        },

        hideWithTransition: function () {
            var that = this
                , timeout = setTimeout(function () {
                    that.$element.off($.support.transition.end);
                    that.hideModal();
                }, 500);

            this.$element.one($.support.transition.end, function () {
                clearTimeout(timeout);
                that.hideModal();
            });
        },

        hideModal: function () {
            var prop = this.options.height ? 'height' : 'max-height';
            var value = this.options.height || this.options.maxHeight;

            if (value){
                this.$element.find('.modal-body')
                    .css('overflow', '')
                    .css(prop, '');
            }

            this.$element
                .hide()
                .trigger('hidden');
        },

        removeLoading: function () {
            this.$loading.remove();
            this.$loading = null;
            this.isLoading = false;
        },

        loading: function (callback) {
            callback = callback || function () {};

            var animate = this.$element.hasClass('fade') ? 'fade' : '';

            if (!this.isLoading) {
                var doAnimate = $.support.transition && animate;

                this.$loading = $('<div class="loading-mask ' + animate + '">')
                    .append(this.options.spinner)
                    .appendTo(this.$element);

                if (doAnimate) this.$loading[0].offsetWidth; // force reflow

                this.$loading.addClass('in');

                this.isLoading = true;

                doAnimate ?
                    this.$loading.one($.support.transition.end, callback) :
                    callback();

            } else if (this.isLoading && this.$loading) {
                this.$loading.removeClass('in');

                var that = this;
                $.support.transition && this.$element.hasClass('fade')?
                    this.$loading.one($.support.transition.end, function () { that.removeLoading() }) :
                    that.removeLoading();

            } else if (callback) {
                callback(this.isLoading);
            }
        },

        focus: function () {
            var $focusElem = this.$element.find(this.options.focusOn);

            $focusElem = $focusElem.length ? $focusElem : this.$element;

            $focusElem.focus();
        },

        attention: function (){
            // NOTE: transitionEnd with keyframes causes odd behaviour

            if (this.options.attentionAnimation){
                this.$element
                    .removeClass('animated')
                    .removeClass(this.options.attentionAnimation);

                var that = this;

                setTimeout(function () {
                    that.$element
                        .addClass('animated')
                        .addClass(that.options.attentionAnimation);
                }, 0);
            }


            this.focus();
        },


        destroy: function () {
            var e = $.Event('destroy');
            this.$element.trigger(e);
            if (e.isDefaultPrevented()) return;

            this.teardown();
        },

        teardown: function () {
            if (!this.$parent.length){
                this.$element.remove();
                this.$element = null;
                return;
            }

            if (this.$parent !== this.$element.parent()){
                this.$element.appendTo(this.$parent);
            }

            this.$element.off('.modal');
            this.$element.removeData('modal');
            this.$element
                .removeClass('in')
                .attr('aria-hidden', true);
        }
    };


    /* MODAL PLUGIN DEFINITION
     * ======================= */

    $.fn.modal = function (option, args) {
        return this.each(function () {
            var $this = $(this),
                data = $this.data('modal'),
                options = $.extend({}, $.fn.modal.defaults, $this.data(), typeof option == 'object' && option);

            if (!data) $this.data('modal', (data = new Modal(this, options)));
            if (typeof option == 'string') data[option].apply(data, [].concat(args));
            else if (options.show) data.show()
        })
    };

    $.fn.modal.defaults = {
        keyboard: true,
        backdrop: true,
        loading: false,
        show: true,
        width: null,
        height: null,
        maxHeight: null,
        modalOverflow: false,
        consumeTab: true,
        focusOn: null,
        replace: false,
        resize: false,
        attentionAnimation: 'shake',
        manager: 'body',
        spinner: '<div class="loading-spinner" style="width: 200px; margin-left: -100px;"><div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div></div>',
        backdropTemplate: '<div class="modal-backdrop" />'
    };

    $.fn.modal.Constructor = Modal;


    /* MODAL DATA-API
     * ============== */

    $(function () {
        $(document).off('click.modal').on('click.modal.data-api', '[data-toggle="modal"]', function ( e ) {
            var $this = $(this),
                href = $this.attr('href'),
                $target = $($this.attr('data-target') || (href && href.replace(/.*(?=#[^\s]+$)/, ''))), //strip for ie7
                option = $target.data('modal') ? 'toggle' : $.extend({ remote: !/#/.test(href) && href }, $target.data(), $this.data());

            e.preventDefault();
            $target
                .modal(option)
                .one('hide', function () {
                    $this.focus();
                })
        });
    });
}(window.jQuery);
/*
 * Toastr
 * Version 2.0.1
 * Copyright 2012 John Papa and Hans Fjällemark.
 * All Rights Reserved.
 * Use, reproduction, distribution, and modification of this code is subject to the terms and
 * conditions of the MIT license, available at http://www.opensource.org/licenses/mit-license.php
 *
 * Author: John Papa and Hans Fjällemark
 * Project: https://github.com/CodeSeven/toastr
 */
; (function (define) {
    define(['jquery'], function ($) {
        return (function () {
            var version = '2.0.1';
            var $container;
            var listener;
            var toastId = 0;
            var toastType = {
                error: 'error',
                info: 'info',
                success: 'success',
                warning: 'warning'
            };

            var toastr = {
                clear: clear,
                error: error,
                getContainer: getContainer,
                info: info,
                options: {},
                subscribe: subscribe,
                success: success,
                version: version,
                warning: warning
            };

            return toastr;

            //#region Accessible Methods
            function error(message, title, optionsOverride) {
				
                return notify({
                    type: toastType.error,
                    iconClass: getOptions().iconClasses.error,
                    message: message,
                    optionsOverride: optionsOverride,
                    title: title
                });
            }

            function info(message, title, optionsOverride) {
                return notify({
                    type: toastType.info,
                    iconClass: getOptions().iconClasses.info,
                    message: message,
                    optionsOverride: optionsOverride,
                    title: title
                });
            }

            function subscribe(callback) {
                listener = callback;
            }

            function success(message, title, optionsOverride) {
                return notify({
                    type: toastType.success,
                    iconClass: getOptions().iconClasses.success,
                    message: message,
                    optionsOverride: optionsOverride,
                    title: title
                });
            }

            function warning(message, title, optionsOverride) {
                return notify({
                    type: toastType.warning,
                    iconClass: getOptions().iconClasses.warning,
                    message: message,
                    optionsOverride: optionsOverride,
                    title: title
                });
            }

            function clear($toastElement) {
                var options = getOptions();
                if (!$container) { getContainer(options); }
                if ($toastElement && $(':focus', $toastElement).length === 0) {
                    $toastElement[options.hideMethod]({
                        duration: options.hideDuration,
                        easing: options.hideEasing,
                        complete: function () { removeToast($toastElement); }
                    });
                    return;
                }
                if ($container.children().length) {
                    $container[options.hideMethod]({
                        duration: options.hideDuration,
                        easing: options.hideEasing,
                        complete: function () { $container.remove(); }
                    });
                }
            }
            //#endregion

            //#region Internal Methods

            function getDefaults() {
                return {
                    tapToDismiss: true,
                    toastClass: 'toast',
                    containerId: 'toast-container',
                    debug: false,

                    showMethod: 'fadeIn', //fadeIn, slideDown, and show are built into jQuery
                    showDuration: 300,
                    showEasing: 'swing', //swing and linear are built into jQuery
                    onShown: undefined,
                    hideMethod: 'fadeOut',
                    hideDuration: 1000,
                    hideEasing: 'swing',
                    onHidden: undefined,

                    extendedTimeOut: 1000,
                    iconClasses: {
                        error: 'toast-error',
                        info: 'toast-info',
                        success: 'toast-success',
                        warning: 'toast-warning'
                    },
                    iconClass: 'toast-info',
                    positionClass: 'toast-top-right',
                    timeOut: 5000, // Set timeOut and extendedTimeout to 0 to make it sticky
                    titleClass: 'toast-title',
                    messageClass: 'toast-message',
                    target: 'body',
                    closeHtml: '<button>&times;</button>',
                    newestOnTop: true
                };
            }

            function publish(args) {
                if (!listener) {
                    return;
                }
                listener(args);
            }

            function notify(map) {
                var
                    options = getOptions(),
                    iconClass = map.iconClass || options.iconClass;

                if (typeof (map.optionsOverride) !== 'undefined') {
                    options = $.extend(options, map.optionsOverride);
                    iconClass = map.optionsOverride.iconClass || iconClass;
                }

                toastId++;

                $container = getContainer(options);
                var
                    intervalId = null,
                    $toastElement = $('<div/>'),
                    $titleElement = $('<div/>'),
                    $messageElement = $('<div/>'),
                    $closeElement = $(options.closeHtml),
                    response = {
                        toastId: toastId,
                        state: 'visible',
                        startTime: new Date(),
                        options: options,
                        map: map
                    };

                if (map.iconClass) {
                    $toastElement.addClass(options.toastClass).addClass(iconClass);
                }

                if (map.title) {
                    $titleElement.append(map.title).addClass(options.titleClass);
                    $toastElement.append($titleElement);
                }

                if (map.message) {
                    $messageElement.append(map.message).addClass(options.messageClass);
                    $toastElement.append($messageElement);
                }

                if (options.closeButton) {
                    $closeElement.addClass('toast-close-button');
                    $toastElement.prepend($closeElement);
                }

                $toastElement.hide();
                if (options.newestOnTop) {
                    $container.prepend($toastElement);
                } else {
                    $container.append($toastElement);
                }


                $toastElement[options.showMethod](
                    { duration: options.showDuration, easing: options.showEasing, complete: options.onShown }
                );
                if (options.timeOut > 0) {
                    intervalId = setTimeout(hideToast, options.timeOut);
                }

                $toastElement.hover(stickAround, delayedhideToast);
                if (!options.onclick && options.tapToDismiss) {
                    $toastElement.click(hideToast);
                }
                if (options.closeButton && $closeElement) {
                    $closeElement.click(function (event) {
                        event.stopPropagation();
                        hideToast(true);
                    });
                }

                if (options.onclick) {
                    $toastElement.click(function () {
                        options.onclick();
                        hideToast();
                    });
                }

                publish(response);

                if (options.debug && console) {
                    console.log(response);
                }

                return $toastElement;

                function hideToast(override) {
                    if ($(':focus', $toastElement).length && !override) {
                        return;
                    }
                    return $toastElement[options.hideMethod]({
                        duration: options.hideDuration,
                        easing: options.hideEasing,
                        complete: function () {
                            removeToast($toastElement);
                            if (options.onHidden) {
                                options.onHidden();
                            }
                            response.state = 'hidden';
                            response.endTime = new Date(),
                                publish(response);
                        }
                    });
                }

                function delayedhideToast() {
                    if (options.timeOut > 0 || options.extendedTimeOut > 0) {
                        intervalId = setTimeout(hideToast, options.extendedTimeOut);
                    }
                }

                function stickAround() {
                    clearTimeout(intervalId);
                    $toastElement.stop(true, true)[options.showMethod](
                        { duration: options.showDuration, easing: options.showEasing }
                    );
                }
            }
            function getContainer(options) {
                if (!options) { options = getOptions(); }
                $container = $('#' + options.containerId);
                if ($container.length) {
                    return $container;
                }
                $container = $('<div/>')
                    .attr('id', options.containerId)
                    .addClass(options.positionClass);
                $container.appendTo($(options.target));
                return $container;
            }

            function getOptions() {
                return $.extend({}, getDefaults(), toastr.options);
            }

            function removeToast($toastElement) {
                if (!$container) { $container = getContainer(); }
                if ($toastElement.is(':visible')) {
                    return;
                }
                $toastElement.remove();
                $toastElement = null;
                if ($container.children().length === 0) {
                    $container.remove();
                }
            }
            //#endregion

        })();
    });
}(typeof define === 'function' && define.amd ? define : function (deps, factory) {
    if (typeof module !== 'undefined' && module.exports) { //Node
        module.exports = factory(require(deps[0]));
    } else {
        window['toastr'] = factory(window['jQuery']);
    }
}));

if(window.toastr){
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "positionClass": "toast-top-right",
        "onclick": null,
        "showDuration": "1000",
        "hideDuration": "1000",
        "timeOut": "3000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
}

var Win=function(){
    var defaultConfig={
        title:'提示',
        url:false,
        content:'',
        modelType:'open',
        yesText:'确定',
        noText:'取消',
        hideClose:true,
        hideFooter:false,
        callback:false,// win 打开完成回调函数
        yesFunc:function(){},// 点击确定按钮触发的方法
        noFunc:function(){}// 点击取消按钮触发的方法
    };

    function parseHtml(config) {
        var tmpl = '<div class="modal" tabindex="-1">' +
            '<div class="modal-header">' +
            '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>' +
            '<h4 class="modal-title">' + config.title + '</h4>' +
            '</div>' +
            '<div class="modal-body">' + config.content + '</div>';

        if (config.url) {

            //confirm 加 按钮
            if (config.modelType == 'confirm') {
                tmpl += '<div class="modal-footer">';
                tmpl += '<a data-dismiss="modal" class="btn btn-default _model-close-btn">' + config.noText + '</a>' +
                '<a class="btn btn-primary _modal-sure-btn">' + config.yesText + '</a>';
                tmpl += '</div>';
            }
        } else {
            if(!config.hideFooter){
                tmpl += '<div class="modal-footer">';
                if (config.modelType == 'alert') {
                    tmpl += '<a class="btn btn-primary _modal-sure-btn">' + config.yesText + '</a>';
                } else {
                    tmpl +=
                    '<a class="btn btn-primary _modal-sure-btn">' + config.yesText + '</a>'+'<a data-dismiss="modal" class="btn btn-default _model-close-btn">' + config.noText + '</a>';
                }
                tmpl += '</div>';
            }
        }
        tmpl += '</div>';
        return tmpl;
    }

    var model={};

    /**
     * 打开model
     * @param config - 参看 defaultConfig
     * @returns {*}
     */
    model.open = function (config) {
        if (config.hideClose) {
            config.keyboard = false;
            config.backdrop = 'static';
        }
        var html=parseHtml($.extend({},defaultConfig,config));
        var obj=$(html);
        var _win;
        if(config.url){
            obj.find('.modal-body').html('<div style="width: 40%;margin: 10px auto 10px;"><div style="margin-bottom: 5px;" class="progress progress-striped active"><div class="progress-bar progress-bar-success" style="width: 100%"></div></div><div class="text-center">加载页面中。。。</div></div>');
            _win=obj.modal(config);
            _win.find('.modal-body').load(config.url, '', function(){
                _win.modal('layout','123');
                if(config.callback){
                    config.callback();
                }
            });
        }else{
            _win=obj.modal(config);
            if(config.callback){
                config.callback();
            }
        }

        if(config.hideClose){
            obj.find('.modal-header .close').hide();
        }

        obj.on('click','._modal-sure-btn',function(){
            var _this=this;
            if(config.yesFunc){
                var result=config.yesFunc(_this);
                if(result==false){
                    return false;
                }
            }
            _win.modal('hide');
        });

        obj.on('click','._model-close-btn',function(){
            if(config.noFunc){
                var result=config.noFunc();
                if(result==false){
                    return false;
                }
            }
        });
        return _win;
    };
    model.alert = function (config) {
        config.modelType='alert';
        var _win=model.open(config);
        return _win;
    };

    model.confirm = function (config) {
        config.modelType='confirm';
        var _win=model.open(config);
        return _win;
    };
    model.go=function(win){
        var modelHeight=win.height();
        var winHeight=$(window).height();
        var marginTop=0;
        if(modelHeight>winHeight){
            marginTop=-winHeight/2;
        }else{
            marginTop=-modelHeight/2;
        }
        win.css("margin-top",marginTop);
        return win;
    }
    return model;
}();
