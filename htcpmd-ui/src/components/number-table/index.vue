<template>
	<!-- only support 1 or 2 dimension full matrix -->
	<el-col
		:span="span"
		:class="oneColumn ? 'number-table one-column' : 'number-table'"
	>
		<el-table
			:data="data"
			:show-header="header"
			:border="border"
			size="mini"
		>
			<template v-if="oneColumn">
				<el-table-column align="center">
					<template slot-scope="scope">
						<el-input-number
							v-model="data[scope.$index]"
							:precision="precision"
							:controls="controls"
							:disabled="disabled"
							class="detail-number"
						></el-input-number>
					</template>
				</el-table-column>
			</template>
			<template v-else>
				<el-table-column
					v-for="index in data[0].length"
					:key="index"
					align="center"
				>
					<template slot-scope="scope">
						<el-input-number
							v-model="scope.row[index - 1]"
							:precision="precision"
							:controls="controls"
							:disabled="disabled"
							class="detail-number"
						></el-input-number>
					</template>
				</el-table-column>
			</template>
		</el-table>
	</el-col>
</template>

<script>
export default {
	name: "number-table",
	props: {
		span: {
			type: Number,
			default() {
				return 6;
			}
		},
		data: {
			type: Array,
			required: true
		},
		header: {
			type: Boolean,
			default() {
				return false;
			}
		},
		border: {
			type: Boolean,
			default() {
				return true;
			}
		},
		precision: {
			type: Number,
			default() {
				return 6;
			}
		},
		controls: {
			type: Boolean,
			default() {
				return false;
			}
		},
		disabled: {
			type: Boolean,
			default() {
				return false;
			}
		}
	},
	computed: {
		oneColumn() {
			return !Array.isArray(this.data[0]);
		}
	},
	watch: {
		data(newVal) {
			this.$emit("update", this.data);
		}
	}
};
</script>

<style lang="scss">
.number-table {
	&.one-column {
		tbody {
			display: flex;
			overflow-x: auto;
		}
		.el-input {
			min-width: 32px;
			width: 100%;
		}
	}
	.el-input-number--small {
		width: 100%;
	}
	max-width: 320px;
	min-width: 280px;
	.detail-number {
		width: 100%;
		input {
			border: none !important;
			background: none !important;
			padding: initial !important;
			cursor: default !important;
			color: black !important;
		}
	}
}
</style>
