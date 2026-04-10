# LUVUE

Type-safe Vue.js templates in Java using [luvml](https://github.com/luvml/luvml).

Write Vue.js components with compile-time guarantees, IDE autocomplete, and zero template syntax errors.

## Installation

```xml
<dependency>
    <groupId>io.github.luvml</groupId>
    <artifactId>luvue</artifactId>
    <version>2.0</version>
</dependency>
```

**Prerequisites**: Requires luvml 2.0+. See [luvml tutorial](../luvml/luvml_tutorial.md) for HTML/XML generation basics.

## Quick Start

```java
import static luvml.E.*;
import static luvml.A.*;
import static luvue.VA.*;  // Vue attributes

var todoList = div(id("app"),
    input(vModel("newTodo"), vOn("keyup.enter", "addTodo")),
    ul(
        li(
            vFor("todo in todos"),
            v$key("todo.id"),
            vClick("toggleTodo(todo)"),
            v$class("{ completed: todo.done }"),
            text("{{ todo.text }}")
        )
    )
);
```

Renders to:
```html
<div id="app">
  <input v-model="newTodo" @keyup.enter="addTodo">
  <ul>
    <li v-for="todo in todos"
        :key="todo.id"
        @click="toggleTodo(todo)"
        :class="{ completed: todo.done }">
      {{ todo.text }}
    </li>
  </ul>
</div>
```

## Naming Conventions

Luvue uses consistent naming to avoid conflicts with standard HTML attributes:

| Vue Syntax | Luvue Method | Example |
|------------|--------------|---------|
| `v-if="condition"` | `vIf("condition")` | `vIf("user.isLoggedIn")` |
| `v-for="item in items"` | `vFor("item in items")` | `vFor("product in products")` |
| `:key="id"` | `v$key("id")` | `v$key("user.id")` |
| `:class="obj"` | `v$class("obj")` | `v$class("{ active: isActive }")` |
| `@click="handler"` | `vClick("handler")` | `vClick("handleSubmit")` |
| `@submit="fn"` | `vSubmit("fn")` | `vSubmit("onSubmit")` |

**Convention**:
- **Directives**: `v` prefix (e.g., `vIf`, `vFor`, `vShow`)
- **Bindings** (`:attr`): `v$` prefix (e.g., `v$key`, `v$src`, `v$style`)
- **Events** (`@event`): `v` prefix (e.g., `vClick`, `vChange`, `vInput`)

## Core Features

### Directives

```java
// Conditional rendering
div(vIf("user"), text("Welcome")),
div(vElseIf("guest"), text("Please login")),
div(vElse(), text("Error"))

// List rendering
ul(
    li(vFor("item in items"), v$key("item.id"), text("{{ item.name }}"))
)

// Show/hide
div(vShow("isVisible"), text("Toggle me"))

// Two-way binding
input(vModel("username"))

// Text/HTML content
p(vText("message"))
div(vHtml("rawHtml"))
```

### Property Bindings

```java
// Common bindings (syntactic sugar)
img(v$src("user.avatar"), v$alt("user.name"))
a(v$href("item.url"), text("Link"))
div(v$style("{ color: userColor }"))
button(v$disabled("!isValid"))

// Generic binding for any attribute
div(vBind("data-id", "user.id"))
```

### Event Handlers

```java
// Common events (syntactic sugar)
button(vClick("handleClick"))
input(vInput("onInput"), vChange("onChange"))
form(vSubmit("handleSubmit"))
input(vKeyup("handleKey"), vKeydown("handleKey"))

// Generic event for any event type
div(vOn("mouseover", "handleHover"))
```

## Complete Examples

### Todo App

```java
import static luvml.E.*;
import static luvml.A.*;
import static luvue.VA.*;

var todoApp = div(id("app"),
    h1("My Todos"),

    // Input form
    form(vSubmit("addTodo"),
        input(
            typeText(),
            vModel("newTodo"),
            placeholder("What needs to be done?")
        ),
        button(typeSubmit(), "Add")
    ),

    // Todo list
    ul(
        li(
            vFor("todo in todos"),
            v$key("todo.id"),
            v$class("{ done: todo.completed }"),

            input(
                typeCheckbox(),
                vModel("todo.completed")
            ),
            E.span(vClick("editTodo(todo)"), text("{{ todo.text }}")),
            button(vClick("removeTodo(todo)"), "×")
        )
    ),

    // Footer
    p(vShow("todos.length > 0"),
        text("{{ todos.filter(t => !t.completed).length }} items left")
    )
);
```

### Dynamic Form

```java
var userForm = form(vSubmit("handleSubmit"),
    div(class_("form-group"),
        label(for_("email"), "Email"),
        input(
            typeEmail(),
            id("email"),
            vModel("form.email"),
            v$class("{ error: errors.email }"),
            vInput("validateEmail")
        ),
        p(vIf("errors.email"), class_("error"), text("{{ errors.email }}"))
    ),

    div(class_("form-group"),
        label("Role"),
        select(vModel("form.role"),
            option(v$value("'user'"), "User"),
            option(v$value("'admin'"), "Admin")
        )
    ),

    button(
        typeSubmit(),
        v$disabled("!isValid"),
        "Submit"
    )
);
```

### Conditional Components

```java
var dashboard = div(
    // Admin panel
    div(vIf("user.role === 'admin'"), class_("admin-panel"),
        h2("Admin Dashboard"),
        button(vClick("manageUsers"), "Manage Users")
    ),

    // User content
    div(vElseIf("user.role === 'user'"), class_("user-panel"),
        h2("Welcome ", text("{{ user.name }}")),
        p(text("{{ user.email }}"))
    ),

    // Guest content
    div(vElse(), class_("guest-panel"),
        h2("Please Log In"),
        button(vClick("showLogin"), "Login")
    )
);
```

### Dynamic Styling

```java
var styledCard = div(
    class_("card"),
    v$style("{" +
        "backgroundColor: theme.bgColor," +
        "color: theme.textColor," +
        "borderColor: isActive ? 'blue' : 'gray'" +
    "}"),
    v$class("{" +
        "active: isActive," +
        "disabled: !isEnabled," +
        "'card-large': size === 'large'" +
    "}"),

    text("{{ cardContent }}")
);
```

## API Reference

### `VA` Class Methods

**Directives**:
- `vIf(String condition)` - Conditional rendering
- `vElseIf(String condition)` - Else-if branch
- `vElse()` - Else branch
- `vShow(String condition)` - CSS-based toggle
- `vFor(String expression)` - List rendering
- `vModel(String binding)` - Two-way data binding
- `vText(String expression)` - Text content
- `vHtml(String expression)` - Raw HTML content

**Bindings** (`:attr`):
- `vBind(String attr, String expr)` - Generic binding
- `v$key(String expr)` - List key (`:key`)
- `v$src(String expr)` - Image/media source
- `v$href(String expr)` - Link URL
- `v$style(String expr)` - Inline styles
- `v$class(String expr)` - CSS classes
- `v$disabled(String expr)` - Disabled state
- `v$value(String expr)` - Input value
- `v$title(String expr)` - Title attribute
- `v$alt(String expr)` - Alt text

**Events** (`@event`):
- `vOn(String event, String handler)` - Generic event
- `vClick(String handler)` - Click event
- `vChange(String handler)` - Change event
- `vInput(String handler)` - Input event
- `vSubmit(String handler)` - Form submit
- `vKeyup(String handler)` - Key up
- `vKeydown(String handler)` - Key down

## Tips

1. **Learn luvml first**: Luvue extends luvml. Read the [luvml tutorial](../luvml/luvml_tutorial.md) for basics on elements, attributes, and rendering.

2. **Use `E.span()` for conflicts**: Since `VA` has event handlers that might conflict with element names, import `luvml.E` and use `E.span()` when needed.

3. **String expressions stay as-is**: Vue template expressions are plain strings - no special escaping needed:
   ```java
   v$style("{ color: item.color }")  // Not escaped
   ```

4. **Combine with standard attributes**: Mix Vue and HTML attributes freely:
   ```java
   button(
       class_("btn", "btn-primary"),  // Standard HTML
       vClick("submit"),               // Vue event
       v$disabled("!isValid")          // Vue binding
   )
   ```

## Links

- **luvml**: [https://github.com/luvml/luvml](https://github.com/luvml/luvml)
- **luvml Tutorial**: [luvml_tutorial.md](../luvml/luvml_tutorial.md)
- **Vue.js**: [https://vuejs.org](https://vuejs.org)

---

**Type-safe Vue templates for the JVM**
