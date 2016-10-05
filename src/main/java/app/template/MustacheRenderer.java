package app.template;

import com.samskivert.mustache.Mustache;

import static support.ContentOf.resource;

public class MustacheRenderer {

    public static String renderTemplate(String templateName, Object data) {
        return Mustache
                .compiler()
                .emptyStringIsFalse(true)
                .escapeHTML(false)
                .defaultValue("")
                .compile(resource(templateName))
                .execute(data);
    }
}
