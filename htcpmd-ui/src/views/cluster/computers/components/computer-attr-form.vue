<template>
  <objectAttrForm :object="computer" :keys="keys" :inTableExpand="inTableExpand"></objectAttrForm>
</template>
<script>
import objectAttrForm from "@/components/object-attr-form";
export default {
  name: "computer-attr-form",
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
        createBy: String,
        label: String,
        hostname: String,
        description: String,
        transport_type: String,
        scheduler_type: String,
        workdir: String,
        shebang: String,
        mpirun_command: String,
        prepend_text: String,
        append_text: String
      }
    }
  }
}
</script>
