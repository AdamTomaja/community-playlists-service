package com.cydercode.service.embed;

import com.cydercode.model.MusicItem;

public interface EmbedCodeGenerator {

  String generateEmbedCode(MusicItem item);

  default String replaceExtId(String template, String extId){
    return replaceTag(template, "ext_id", extId);
  }

  default String replaceTag(String template, String tag, String value) {
    return template.replace("{{%s}}".formatted(tag), value);
  }
}
