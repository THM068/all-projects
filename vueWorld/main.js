const app = Vue.createApp({
    data() {
        return {
            showTitle: true,
            title: "Socks",
            description: "Amazing blue socks..",
            count: 0,
            isButtonDisabled: true,
            names: ['Thando', 'Kerrie', "Thomas", "Alexandra"]

        }
    },
    methods: {
        changeTitle(title) {
            console.log("You clicked me..")
            this.title = title.toUpperCase()
        },
        toggleTitle() {
            this.showTitle = !this.showTitle
        },
        handleEvents(event) {
            console.log(event)
        }

    }
})

const todoApp = Vue.createApp({
    data() {
        return {
            names: "Thando, Kerrie, Thomas, Alexandra",
            rawHtml: '<span style="color: red">This should be red.</span>',
            myId: 'rawId',
            multipleAttr: {
                id: 'container',
                class: "small-field"
            }
        };
    }
})