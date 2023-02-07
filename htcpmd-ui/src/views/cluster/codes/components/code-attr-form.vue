<template>
  <objectAttrForm :object="code" :keys="keys" :inTableExpand="inTableExpand"></objectAttrForm>
</template>
<script>
import objectAttrForm from "@/components/object-attr-form";
export default {
  name: "code-attr-form",
  components: { objectAttrForm },
  props: {
    object: {
      default() {
        return {}
      }
    },
    inTableExpand: {
      default() {
        return true
      }
    }
  },
  data() {
    return {
      keys: {
        uuid: String,
        label: String,
        hostname: String,
        description: String,
        remote_exec_path: String,
        input_plugin: String,
        prepend_text: String,
        append_text: String,
        createBy: String,
        createTime: Date,
        updateTime: Date
      }
    }
  }
}
</script>
