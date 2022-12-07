<template>
	<section v-loading="disabled">
		<div class="periodictable">
			<button
				class="item"
				v-for="el in Elements.first"
				:key="el.name_small"
				@click="selectElement(el)"
				@mouseover="showElement(el)"
				:class="[
					`color-${el.group_id}`,
					{ blur: blur && blur != el.group_id },
					selected_id.includes(el.id) ? 'hover-element-class' : ''
				]"
			>
				<span class="small">{{ Math.floor(el.molar * 10) / 10 }}</span>
				{{ el.name_small }}
				<span class="el_number">{{ el.number }}</span>
			</button>
		</div>
		<div class="periodictable">
			<div class="elementinfo group" v-if="blur && info.group_id">
				<div class="title">
					<div class="sembol" :class="`color-${blur}`">
						{{ $t("types." + blur) }}
					</div>
				</div>
			</div>
			<div class="elementinfo" v-if="info.group_id && !blur">
				<div class="header">
					<div class="title">
						<div class="sembol" :class="`color-${info.group_id}`">
							{{ info.name_small }}
						</div>
						<div class="name">
							<h3>
								{{
									info[
										"name_" + (lang == "en" ? "ing" : lang)
									]
								}}
							</h3>
							<span>{{ $t("types." + info.group_id) }}</span>
						</div>
					</div>
					<div id="bohr-model-container"></div>
				</div>
				<div class="detail">
					<div class="d__item">
						<p class="info">{{ $t("element.number") }}</p>
						<P class="val" :class="`color-${info.group_id}`">{{
							info.number
						}}</P>
					</div>
					<div class="d__item">
						<p class="info">{{ $t("element.molar") }}</p>
						<P class="val" :class="`color-${info.group_id}`"
							>{{ Math.floor(info.molar * 20) / 20 }}
							<span>g/mol</span>
						</P>
					</div>
				</div>
			</div>
			<button
				type="button"
				class="item"
				v-for="el in Elements.second"
				:key="el.name_small"
				@click="selectElement(el)"
				@mouseover="showElement(el)"
				:class="[
					{
						second: el.column === 1,
						blur: blur && blur != el.group_id
					},
					`color-${el.group_id}`,
					selected_id.includes(el.id) ? 'hover-element-class' : ''
				]"
			>
				<span class="small">{{ Math.floor(el.molar * 10) / 10 }}</span>
				{{ el.name_small }}
				<span class="el_number">{{ el.number }}</span>
			</button>
		</div>
		<div class="periodictable">
			<button
				type="button"
				class="item"
				@click="selectElement(el)"
				@mouseover="showElement(el)"
				v-for="el in Elements.body"
				:key="el.name_small"
				:class="[
					{ blur: blur && blur != el.group_id },
					`color-${el.group_id}`,
					selected_id.includes(el.id) ? 'hover-element-class' : ''
				]"
			>
				<span class="small">{{
					el.molar ? Math.floor(el.molar * 10) / 10 : ""
				}}</span>
				{{ el.name_small }}
				<span class="el_number">{{ el.number }}</span>
			</button>
		</div>
		<div class="periodictable p-footer">
			<button
				type="button"
				class="item"
				v-for="el in Elements.bottom"
				@click="selectElement(el)"
				@mouseover="showElement(el)"
				:key="el.name_small"
				:class="[
					{
						x2: el.column === 2,
						x: el.column === 16,
						blur: blur && blur != el.group_id
					},
					`color-${el.group_id}`,
					selected_id.includes(el.id) ? 'hover-element-class' : ''
				]"
			>
				<span class="small">{{ Math.floor(el.molar * 10) / 10 }}</span>
				{{ el.name_small }}
				<span class="el_number">{{ el.number }}</span>
			</button>
		</div>
		<modal :element="ModalData" v-if="showModal" @closeModal="closeModal" />
	</section>
</template>

<script>
import "atomic-bohr-model/dist/atomicBohrModel.min.js";
import Modal from "./modal";
import { mapGetters, mapActions, mapMutations } from "vuex";
export default {
	name: "PeriodicTable",
	data() {
		return {
			blur: null,
			blurset: false,
			showModal: false,
			ModalData: [],
			info: {},
			element: {
				number: 78
			},
			animations: true,
			nonMetal: [1, 6, 7, 8, 15, 16, 34],
			alkali: [3, 11, 19, 37, 55, 87],
			akaliEarth: [4, 12, 20, 38, 56, 88],
			// prettier-ignore
			transitionMetal: [21, 22,23, 24, 25, 26, 27, 28, 29, 30, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 72, 73, 74, 75, 76, 77, 78, 79, 80, 104, 105, 106, 107, 108, 109, 110, 111, 112],
			postTransition: [13, 31, 49, 50, 81, 82, 83, 113, 114, 115, 116],
			metalloid: [5, 14, 32, 33, 51, 52, 84],
			halogen: [9, 17, 35, 53, 85, 117],
			noble: [2, 10, 18, 36, 54, 86, 118],
			lanthanoid: [
				57,
				58,
				59,
				60,
				61,
				62,
				63,
				64,
				65,
				66,
				67,
				68,
				69,
				70,
				71
			],
			actinoid: [
				89,
				90,
				91,
				92,
				93,
				94,
				95,
				96,
				97,
				98,
				99,
				100,
				101,
				102,
				103
			],
			atomModel: null,
			selected_elements: [],
			selected_id: [],
			disabled: false
		};
	},
	props: {
		updated: { require: false, default: "" }
	},
	components: {
		Modal
	},
	computed: {
		...mapGetters("periodic", ["Elements", "darkMode", "lang"])
	},
	watch: {
		element: function() {
			this.atomModel.destroy();
			var orbitalRotationConfig = {
				pattern: {
					alternating: false,
					clockwise: false,
					preset: "cubedNegative"
				}
			};
			this.atomModel.numElectrons = this.atomModel.ensureAtomicExistence(
				this.element.number
			);
			this.atomModel.idNumber = this.element.number;
			this.atomModel.nucleusColor = this.classify(this.element)[3];
			this.atomModel.electronColor = this.classify(this.element)[2];
			this.atomModel.orbitalColor = this.classify(this.element)[3];
			this.atomModel._redrawAtom();
			if (this.animations) {
				this.atomModel.rotateOrbitals(orbitalRotationConfig);
			}
		},
		animations: function() {
			this.atomModel.destroy();
			var orbitalRotationConfig = {
				pattern: {
					alternating: false,
					clockwise: false,
					preset: "cubedNegative"
				}
			};
			this.atomModel._redrawAtom();
			if (this.animations) {
				this.atomModel.rotateOrbitals(orbitalRotationConfig);
			}
		},
		updated: function() {
			this.disabled = false;
			let elements = this.updated === "" ? [] : this.updated.split("-");
			let selected_elements = [];
			let selected_id = [];

			elements.forEach(el => {
				let index = this.selected_elements.findIndex(name => {
					return el === name;
				});
				if (index !== -1) {
					selected_elements.push(el);
					selected_id.push(this.selected_id[index]);
				} else {
					let found = this.Elements.first.find(item => {
						return item.name_small === el;
					});
					found === undefined &&
						(found = this.Elements.second.find(item => {
							return item.name_small === el;
						}));
					found === undefined &&
						(found = this.Elements.body.find(item => {
							return item.name_small === el;
						}));
					found === undefined &&
						(found = this.Elements.bottom.find(item => {
							return item.name_small === el;
						}));
					if (found === undefined) {
						this.disabled = true;
					} else {
						selected_elements.push(el);
						selected_id.push(found.id);
					}
					console.log(found);
				}
			});
			this.selected_elements = selected_elements;
			this.selected_id = selected_id;
		}
	},
	methods: {
		...mapActions("periodic", ["getElements"]),
		selectElement(element) {
			if (element.number === "57-71" || element.number === "89-103") {
				return;
			}
			if (this.selected_id.includes(element.id)) {
				this.selected_id.remove(element.id);
				this.selected_elements.remove(element.name_small);
			} else {
				this.selected_id.push(element.id);
				this.selected_elements.push(element.name_small);
			}
			this.$emit("selectedElements", this.selected_elements);
		},
		closeModal() {
			this.ModalData = [];
			this.showModal = false;
		},
		showElement(element) {
			if (element.number === "57-71" || element.number === "89-103") {
				return;
			}
			this.info = element;
			if (!this.atomModel) {
				if (!this.atomModelInitialized)
					setTimeout(() => {
						var atomicConfig = {
							containerId: "#bohr-model-container",
							numElectrons: this.info.number,
							nucleusColor: this.classify(this.info)[3],
							electronRadius: 1.2,
							electronColor: this.classify(this.info)[2],
							// orbitalWidth: 0.05,
							orbitalColor: this.classify(this.info)[3],
							idNumber: this.info.number,
							animationTime: 0,
							orbitalRotationConfig: {
								pattern: {
									alternating: false,
									clockwise: false,
									preset: "cubedNegative"
								}
							},
							symbolOffset: 2.5,
							drawSymbol: true
						};
						this.atomModel = new Atom(atomicConfig);
						this.element = element;
					}, 10);
				this.atomModelInitialized = true;
			} else {
				this.element = element;
			}
		},
		blurOthers(group) {
			this.blur = group;
		},
		openDarkMode() {
			this.TOGGLE_DARK_MODE();
		},
		langSwitch() {
			this.LANG_SWITCH();
		},
		// Determine element styles
		classify(element) {
			var n = Number(element.number);
			if (this.nonMetal.includes(n)) {
				if ([7, 8].indexOf(n) > -1) {
					return [
						"Diatomic Nonmetal",
						"nonMetal",
						"rgba(91, 93, 153, 0.9)",
						"rgba(51, 53, 113, 1)"
					];
				} else {
					return [
						"Polyatomic Nonmetal",
						"nonMetal",
						"rgba(86, 88, 148, 0.9)",
						"rgba(56, 58, 118, 1)"
					];
				}
			} else if (this.alkali.includes(n)) {
				return [
					"Alkali Metal",
					"alkali",
					"rgba(120, 80, 90, 0.9)",
					"rgba(85, 45, 55, 1)"
				];
			} else if (this.akaliEarth.includes(n)) {
				return [
					"Alkali Earth Metal",
					"alkaliEarth",
					"rgba(133, 113, 101, 0.9)",
					"rgba(83, 63, 51, 1)"
				];
			} else if (this.transitionMetal.includes(n)) {
				return [
					"Transition Metal",
					"transitionMetal",
					"rgba(99, 113, 138, 0.9)",
					"rgba(54, 68, 93, 1)"
				];
			} else if (this.postTransition.includes(n)) {
				return [
					"Post Transition Metal",
					"postTransition",
					"rgba(74, 134, 119, 0.9)",
					"rgba(34, 84, 79, 1)"
				];
			} else if (this.halogen.includes(n)) {
				return [
					"Halogen",
					"halogen",
					"rgba(122, 120, 181, 0.9)",
					"rgba(57, 55, 106, 1)"
				];
			} else if (this.noble.includes(n)) {
				return [
					"Noble Gas",
					"noble",
					"rgba(136, 100, 170, 0.9)",
					"rgba(76, 40, 110, 1)"
				];
			} else if (this.lanthanoid.includes(n)) {
				return [
					"Lanthanoid",
					"lanthanoid",
					"rgba(120, 107, 151, 0.9)",
					"rgba(70, 57, 101, 1)"
				];
			} else if (this.actinoid.includes(n)) {
				return [
					"Actinoid",
					"actinoid",
					"rgba(110, 89, 121, 0.9)",
					"rgba(62, 41, 73, 1)"
				];
			} else if (this.metalloid.includes(n)) {
				return [
					"Metalloid",
					"metalloid",
					"rgba(74, 114, 146, 0.9)",
					"rgba(27, 67, 99, 1)"
				];
			}
		}
	},
	created() {
		this.getElements();
	},
	mounted() {}
};
</script>

<style lang="scss">
@import "@/assets/style/variable.scss";
.elementinfo {
	position: absolute;
	left: calc(((#{$element-size} + 0.46vw) * 2) + 0.3vw);
	bottom: 0;
	width: calc(((#{$element-size} + 0.46vw) * 10) - 0.3vw);
	display: flex;
	z-index: 999;
	flex-direction: column;
	background: transparent;
	color: #000;
	&.group {
		.title {
			font-size: 2em;
			.sembol {
				width: 100%;
				background: transparent;
			}
		}
		padding: 20px;
	}
	.header {
		display: flex;
		justify-content: space-around;
		align-self: center;
		height: 8rem;
		width: 100%;
		padding: 0 1rem;
		#bohr-model-container {
			width: 8rem;
			height: 8rem;
			transform: scale(1.5);
		}
		.title {
			display: flex;
			margin-left: 1rem;
			align-items: center;
			.name {
				padding: 1rem;
				h3 {
					font-weight: 500;
					font-size: 1.5rem;
					line-height: 1.1;
					margin-bottom: 0.7rem;
				}
				span {
					opacity: 0.4;
					font-size: 0.9rem;
				}
			}
			.sembol {
				background: #35404d;
				color: #fff;
				width: 5rem;
				height: 5rem;
				display: flex;
				font-size: 2vw;
				align-items: center;
				justify-content: center;
				border-radius: 8px;
				&.color-1 {
					color: $non-matel;
					border-bottom: 0.4rem solid $non-matel;
				}
				&.color-2 {
					color: $alkal-metal;
					border-bottom: 0.4rem solid $alkal-metal;
				}
				&.color-3 {
					color: $alkaline-metal;
					border-bottom: 0.4rem solid $alkaline-metal;
				}
				&.color-4 {
					color: $transition-metal;
					border-bottom: 0.4rem solid $transition-metal;
				}
				&.color-5 {
					color: $metaloid;
					border-bottom: 0.4rem solid $metaloid;
				}
				&.color-6 {
					color: $metal;
					border-bottom: 0.4rem solid $metal;
				}
				&.color-7 {
					color: $halojen;
					border-bottom: 0.4rem solid $halojen;
				}
				&.color-8 {
					color: $soygaz;
					border-bottom: 0.4rem solid $soygaz;
				}
				&.color-9 {
					color: $lantanit;
					border-bottom: 0.4rem solid $lantanit;
				}
				&.color-10 {
					color: $actinide;
					border-bottom: 0.4rem solid $actinide;
				}
			}
		}
	}

	.detail {
		display: flex;
		width: 100%;
		margin-top: 20px;
		text-align: center;
		.d__item {
			width: 50%;
			padding: 10px;
			font-weight: 500;
			.info {
				opacity: 0.5;
			}
			.val {
				&.color-1 {
					color: $non-matel;
				}
				&.color-2 {
					color: $alkal-metal;
				}

				&.color-3 {
					color: $alkaline-metal;
				}

				&.color-4 {
					color: $transition-metal;
				}

				&.color-5 {
					color: $metaloid;
				}

				&.color-6 {
					color: $metal;
				}

				&.color-7 {
					color: $halojen;
				}

				&.color-8 {
					color: $soygaz;
				}

				&.color-9 {
					color: $lantanit;
				}

				&.color-10 {
					color: $actinide;
				}
			}
		}
	}
}

.periodictable {
	width: 75vw;
	display: flex;
	flex-wrap: wrap;
	max-width: 100vw;
	position: relative;
	justify-content: space-between;
	&.title {
		margin-bottom: 50px;
		justify-content: center;
		transform: translateX(-10%);
	}
	.item {
		width: $element-size;
		height: $element-size;
		margin: 0.23vw;
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		box-shadow: 0;
		border: 0;
		cursor: pointer;
		font-size: 1vw;
		font-weight: bold;
		position: relative;
		// color: #fff;
		color: #666;
		transform-origin: center;
		opacity: 1;
		transition: all 0.1s ease-in-out;
		.el_number {
			display: block;
			font-size: 50%;
			margin-top: 5px;
			opacity: 0.5;
		}
		&.blur {
			opacity: 0.1;
		}
	}
}

.description {
	width: 100%;
	flex-shrink: 0;
	.item {
		display: flex;
		align-items: center;
		margin-bottom: 10px;
		font-size: 1vw;
		&.types {
			cursor: pointer;
		}
		&.blur {
			opacity: 0.5;
			&.set {
				text-decoration: line-through;
			}
		}
		&::before {
			content: "";
			display: inline-block;
			width: 30px;
			height: 20px;
			margin-right: 5px;
		}
		&.color-1::before {
			background: $non-matel;
		}
		&.color-2::before {
			background: $alkal-metal;
		}
		&.color-3::before {
			background: $alkaline-metal;
		}
		&.color-4::before {
			background: $transition-metal;
		}
		&.color-5::before {
			background: $metaloid;
		}
		&.color-6::before {
			background: $metal;
		}
		&.color-7::before {
			background: $halojen;
		}
		&.color-8::before {
			background: $soygaz;
		}
		&.color-9::before {
			background: $lantanit;
		}
		&.color-10::before {
			background: $actinide;
		}
	}
}

/* //  */
.periodictable {
	@mixin hover-element-class() {
		background: rgba(#fff, 0.3) !important;
		color: #000000;
		transform: scale(1.05);
		opacity: 1;
	}
	.item {
		.small {
			font-size: 50%;
			opacity: 0.5;
		}
		&.color-1 {
			background: rgba($non-matel, 0.7);
			border: 1px solid $non-matel;
		}
		&.color-2 {
			background: rgba($alkal-metal, 0.7);
			border: 1px solid $alkal-metal;
		}
		&.color-3 {
			background: rgba($alkaline-metal, 0.7);
			border: 1px solid $alkaline-metal;
		}
		&.color-4 {
			background: rgba($transition-metal, 0.7);
			border: 1px solid $transition-metal;
		}
		&.color-5 {
			background: rgba($metaloid, 0.7);
			border: 1px solid $metaloid;
		}
		&.color-6 {
			background: rgba($metal, 0.7);
			border: 1px solid $metal;
		}
		&.color-7 {
			background: rgba($halojen, 0.7);
			border: 1px solid $halojen;
		}
		&.color-8 {
			background: rgba($soygaz, 0.7);
			border: 1px solid $soygaz;
		}
		&.color-9 {
			background: rgba($lantanit, 0.7);
			border: 1px solid $lantanit;
		}
		&.color-10 {
			background: rgba($actinide, 0.7);
			border: 1px solid $actinide;
		}
		&:hover {
			@include hover-element-class();
		}
		&.hover-element-class {
			@include hover-element-class();
		}
		&.second {
			margin-right: calc(((#{$element-size} + 0.46vw) * 10) + 0.69vw);
		}
	}
	&.p-footer {
		margin-top: 20px;
		.item {
			&.x2 {
				margin-left: calc((#{$element-size} + 0.46vw) * 2);
			}
			&.x {
				margin-right: calc((#{$element-size} + 0.46vw));
			}
		}
	}
}

.checkbox {
	width: 70px;
	height: 30px;
	background: #fff;
	border-radius: 30px;
	position: relative;
	margin-bottom: 10px;
	margin-top: 20px;
	cursor: pointer;
	transition: 0.3s all ease;
	color: inherit;
	border: 1.4px solid #000;
	&.lang {
		height: 70px;
		background: transparent;
		border: none;

		&::after {
			display: none;
		}
	}
	&::after {
		content: "";
		display: flex;
		width: 20px;
		height: 20px;
		background: #000;
		position: absolute;
		left: 5px;
		top: 3px;
		border-radius: 50%;
	}
	&.dark-active {
		background: #000;
		border: 1.4px solid #fff;
		&::after {
			background: #fff;
			right: 5px;
			left: initial;
		}
	}
	label {
		position: absolute;
		right: calc(-100% - 10px);
		top: 4px;
		font-size: 13px;
		font-weight: 600;
	}
}

.footer {
	display: flex;
	color: inherit;
	position: fixed;
	flex-wrap: wrap;
	align-items: center;
	width: calc(25vw - 30px);
	height: 100vh;
	right: 0;
	top: 0;
	padding: 20px;
	box-shadow: -2px 5px 10px rgba(#000, 0.3);
	h3 {
		font-size: 2vw;
		line-height: 50px;
		&::after {
			content: "";
			display: block;
			width: 100%;
			height: 1px;
			background: currentColor;
			opacity: 0.2;
		}
	}
	.end {
		flex-shrink: 0;
		width: 100%;
		align-self: flex-end;
		display: grid;
		grid-template-rows: auto auto auto auto;
		grid-template-columns: 80px auto;

		.checkbox {
			margin: 5px 0;
		}
		label {
			display: flex;
			align-items: center;
		}
	}
	.text {
		font-size: 15px;
		grid-area: 1 span/2 span/2;
		a {
			color: inherit;
			font-weight: bold;
		}
	}
}
.github {
	grid-area: 1 span/2 span/1;
	width: 100%;
	a {
		background: #323232;
		color: #fff;
		width: 100%;
		display: flex;
		padding: 9px;
		font-size: 14px;
		border-radius: 3px;
		align-items: center;
		justify-content: center;
		text-decoration: none;
		margin-bottom: 30px;
		font-weight: 500;
		svg {
			margin-right: 5px;
		}
	}
}

//  --------- //

.darkMode {
	.periodictable {
		.item {
			&.color-1 {
				background: rgba($non-metal-dark, 0.25);
				border: 1px solid $non-metal-dark;
			}
			&.color-2 {
				background: rgba($alkal-metal-dark, 0.25);
				border: 1px solid $alkal-metal-dark;
			}
			&.color-3 {
				background: rgba($alkaline-metal-dark, 0.25);
				border: 1px solid $alkaline-metal-dark;
			}
			&.color-4 {
				background: rgba($transition-metal-dark, 0.25);
				border: 1px solid $transition-metal-dark;
			}

			&.color-5 {
				background: rgba($metaloid-dark, 0.25);
				border: 1px solid $metaloid-dark;
			}

			&.color-6 {
				background: rgba($metal-dark, 0.25);
				border: 1px solid $metal-dark;
			}
			&.color-7 {
				background: rgba($halojen-dark, 0.25);
				border: 1px solid $halojen-dark;
			}
			&.color-8 {
				background: rgba($soygaz-dark, 0.25);
				border: 1px solid $soygaz-dark;
			}
			&.color-9 {
				background: rgba($lantanit-dark, 0.25);
				border: 1px solid $lantanit-dark;
			}
			&.color-10 {
				background: rgba($actinide-dark, 0.25);
				border: 1px solid $lantanit-dark;
			}
		}
	}
	.description {
		.item {
			&.color-1::before {
				background: $non-metal-dark;
			}
			&.color-2::before {
				background: $alkal-metal-dark;
			}
			&.color-3::before {
				background: $alkaline-metal-dark;
			}
			&.color-4::before {
				background: $transition-metal-dark;
			}
			&.color-5::before {
				background: $metaloid-dark;
			}
			&.color-6::before {
				background: $metal-dark;
			}
			&.color-7::before {
				background: $halojen-dark;
			}
			&.color-8::before {
				background: $soygaz-dark;
			}
			&.color-9::before {
				background: $lantanit-dark;
			}
			&.color-10::before {
				background: $actinide-dark;
			}
		}
	}
	.footer {
		background: #1e2f38;
	}
	.github {
		a {
			background: #eee;
			color: #000;
		}
	}

	.elementinfo {
		color: #fff;
		.sembol {
			&.color-1 {
				color: $non-metal-dark;
				border-bottom: 0.4rem solid $non-metal-dark;
			}
			&.color-2 {
				color: $alkal-metal-dark;
				border-bottom: 0.4rem solid $alkal-metal-dark;
			}
			&.color-3 {
				color: $alkaline-metal-dark;
				border-bottom: 0.4rem solid $alkaline-metal-dark;
			}
			&.color-4 {
				color: $transition-metal-dark;
				border-bottom: 0.4rem solid $transition-metal-dark;
			}
			&.color-5 {
				color: $metaloid-dark;
				border-bottom: 0.4rem solid $metaloid-dark;
			}
			&.color-6 {
				color: $metal-dark;
				border-bottom: 0.4rem solid $metal-dark;
			}
			&.color-7 {
				color: $halojen-dark;
				border-bottom: 0.4rem solid $halojen-dark;
			}
			&.color-8 {
				color: $soygaz-dark;
				border-bottom: 0.4rem solid $soygaz-dark;
			}
			&.color-9 {
				color: $lantanit-dark;
				border-bottom: 0.4rem solid $lantanit-dark;
			}
			&.color-10 {
				color: $actinide-dark;
				border-bottom: 0.4rem solid $actinide-dark;
			}
		}
		.detail {
			.val {
				&.color-1 {
					color: $non-metal-dark;
				}
				&.color-2 {
					color: $alkal-metal-dark;
				}

				&.color-3 {
					color: $alkaline-metal-dark;
				}

				&.color-4 {
					color: $transition-metal-dark;
				}

				&.color-5 {
					color: $metaloid-dark;
				}

				&.color-6 {
					color: $metal-dark;
				}

				&.color-7 {
					color: $halojen-dark;
				}

				&.color-8 {
					color: $soygaz-dark;
				}

				&.color-9 {
					color: $lantanit-dark;
				}

				&.color-10 {
					color: $actinide-dark;
				}
			}
		}
	}
}
</style>
