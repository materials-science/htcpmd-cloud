<template>
  <div class="object-attr-form">
    <el-form label-position="top" :class="inTableExpand ? 'data-table-expand--wrap' : ''">
      <el-form-item v-for="[key, val] in (keys && Object.entries(keys)) || entries" :key="key" :label="key">
        <!-- text -->
        <el-input v-if="keys && val == 'text'" disabled type="textarea" :autosize="{ minRows: 2 }"
          :value="object[key]"></el-input>
        <!-- datetime -->
        <el-date-picker v-else-if="keys && val == 'datetime'" :value="object[key]" type="datetime"
          :placeholder="$_t('no data')" readonly>
        </el-date-picker>
        <!-- user link -->
        <user-link v-else-if="keys && val == 'user-link'" :uuid="object[key]"></user-link>
        <!-- input -->
        <el-input v-else disabled :value="object[key]"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import userLink from "@/components/user-link";
export default {
  name: "object-attr-form",
  components: {
    userLink
  },
  props: {
    object: {
      default() {
        return {}
      }
    },
    keys: {
      default() {
        return null
      }
    },
    inTableExpand: {
      default() {
        return true
      }
    }
  },
  computed: {
    entries() {
      return Object.entries(this.object).filter(([key]) => this.keys === null || key in this.keys)
    }
  }
}
</script>
