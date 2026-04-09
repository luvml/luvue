package luvue;

import luvml.HtmlAttribute;

/**
 * Vue.js attributes DSL for luvml templates.
 *
 * Naming convention:
 * - Directives: vIf, vFor, vShow, vModel, etc. (v-prefix)
 * - Bindings: v$key, v$src, v$style, etc. (v$ prefix for :attr)
 * - Events: vClick, vChange, vInput, etc. (v-prefix for @event)
 *
 * Usage:
 *   import static luvue.VA.*;
 *
 *   div(
 *     vFor("item in items"),
 *     v$key("item.id"),
 *     v$style("`color: ${item.color}`"),
 *     vClick("handleClick(item)")
 *   )
 */
public class VA {

    // ===== Vue Directives =====

    public static HtmlAttribute vIf(String condition) {
        return new HtmlAttribute("v-if", condition);
    }

    public static HtmlAttribute vElse() {
        return new HtmlAttribute("v-else", null);
    }

    public static HtmlAttribute vElseIf(String condition) {
        return new HtmlAttribute("v-else-if", condition);
    }

    public static HtmlAttribute vShow(String condition) {
        return new HtmlAttribute("v-show", condition);
    }

    public static HtmlAttribute vFor(String expression) {
        return new HtmlAttribute("v-for", expression);
    }

    public static HtmlAttribute vModel(String binding) {
        return new HtmlAttribute("v-model", binding);
    }

    public static HtmlAttribute vText(String expression) {
        return new HtmlAttribute("v-text", expression);
    }

    public static HtmlAttribute vHtml(String expression) {
        return new HtmlAttribute("v-html", expression);
    }

    // ===== Vue Bindings (:attr) - v$ prefix =====

    /** Generic bind - use for uncommon attributes */
    public static HtmlAttribute vBind(String attr, String expression) {
        return new HtmlAttribute(":" + attr, expression);
    }

    // Common bindings (syntactic sugar)

    public static HtmlAttribute v$key(String expression) {
        return new HtmlAttribute(":key", expression);
    }

    public static HtmlAttribute v$src(String expression) {
        return new HtmlAttribute(":src", expression);
    }

    public static HtmlAttribute v$href(String expression) {
        return new HtmlAttribute(":href", expression);
    }

    public static HtmlAttribute v$style(String expression) {
        return new HtmlAttribute(":style", expression);
    }

    public static HtmlAttribute v$class(String expression) {
        return new HtmlAttribute(":class", expression);
    }

    public static HtmlAttribute v$disabled(String expression) {
        return new HtmlAttribute(":disabled", expression);
    }

    public static HtmlAttribute v$value(String expression) {
        return new HtmlAttribute(":value", expression);
    }

    public static HtmlAttribute v$title(String expression) {
        return new HtmlAttribute(":title", expression);
    }

    public static HtmlAttribute v$alt(String expression) {
        return new HtmlAttribute(":alt", expression);
    }

    // ===== Vue Event Handlers (@event) - v prefix =====

    /** Generic event handler - use for uncommon events */
    public static HtmlAttribute vOn(String event, String handler) {
        return new HtmlAttribute("@" + event, handler);
    }

    // Common event handlers (syntactic sugar)

    public static HtmlAttribute vClick(String handler) {
        return new HtmlAttribute("@click", handler);
    }

    public static HtmlAttribute vChange(String handler) {
        return new HtmlAttribute("@change", handler);
    }

    public static HtmlAttribute vInput(String handler) {
        return new HtmlAttribute("@input", handler);
    }

    public static HtmlAttribute vSubmit(String handler) {
        return new HtmlAttribute("@submit", handler);
    }

    public static HtmlAttribute vKeyup(String handler) {
        return new HtmlAttribute("@keyup", handler);
    }

    public static HtmlAttribute vKeydown(String handler) {
        return new HtmlAttribute("@keydown", handler);
    }
}
