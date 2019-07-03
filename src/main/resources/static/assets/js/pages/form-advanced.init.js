! function (n) {
    "use strict";
    var e = function () {};
    e.prototype.initSelect2 = function () {
        n('[data-toggle="select2"]').select2()
    }, e.prototype.initMaxLength = function () {
        n("input#defaultconfig").maxlength({
            warningClass: "badge badge-success",
            limitReachedClass: "badge badge-danger"
        }), n("input#thresholdconfig").maxlength({
            threshold: 20,
            warningClass: "badge badge-success",
            limitReachedClass: "badge badge-danger"
        }), n("input#alloptions").maxlength({
            alwaysShow: !0,
            separator: " out of ",
            preText: "You typed ",
            postText: " chars available.",
            validate: !0,
            warningClass: "badge badge-success",
            limitReachedClass: "badge badge-danger"
        }), n("textarea#textarea").maxlength({
            alwaysShow: !0,
            warningClass: "badge badge-success",
            limitReachedClass: "badge badge-danger"
        }), n("input#placement").maxlength({
            alwaysShow: !0,
            placement: "top-left",
            warningClass: "badge badge-success",
            limitReachedClass: "badge badge-danger"
        })
    }, e.prototype.initCustomSelect = function () {
        n('[data-plugin="customselect"]').niceSelect()
    }, e.prototype.initSwitchery = function () {
        n('[data-plugin="switchery"]').each(function (e, t) {
            new Switchery(n(this)[0], n(this).data())
        })
    }, e.prototype.initTouchspin = function () {
        var i = {};
        n('[data-toggle="touchspin"]').each(function (e, t) {
            var a = n.extend({}, i, n(t).data());
            n(t).TouchSpin(a)
        })
    }, e.prototype.init = function () {
        this.initSelect2(), this.initMaxLength(), this.initCustomSelect(), this.initSwitchery(), this.initTouchspin()
    }, n.FormAdvanced = new e, n.FormAdvanced.Constructor = e
}(window.jQuery),
function (e) {
    "use strict";
    window.jQuery.FormAdvanced.init()
}();
