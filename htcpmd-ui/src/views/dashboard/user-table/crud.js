export const crudOptions = (vm) => {
  return {
    pageOptions: {
      compact: true
    },
    options: {
      height: '100%'
    },
    viewOptions: {
      componentType: 'row'
    },
    formOptions: {
      defaultSpan: 12 // 默认的表单 span
    },
    columns: [{
        title: 'ID',
        key: 'id',
        width: 90,
        form: {
          disabled: true
        }
      },
      {
        title: 'Email',
        key: 'email',
        search: {
          disabled: false
        },
      },
      {
        title: 'Username',
        key: 'username',
        search: {
          disabled: false
        },
      },
      {
        title: 'First Name',
        key: 'first_name',
        search: {
          disabled: true
        },
      },
      {
        title: 'Last Name',
        key: 'last_name',
        search: {
          disabled: true
        },
      },
      {
        title: 'Phone',
        key: 'phone',
        type: 'phoneNumber',
        search: {
          disabled: false
        },
      },
      {
        title: 'Groups',
        key: 'groups',
        search: {
          disabled: true
        },
      },
      {
        title: 'Password',
        key: 'password',
        search: {
          disabled: true
        },
      },
      {
        title: 'Last Login',
        key: 'last_login',
        type: 'datetime',
        search: {
          disabled: true
        },
      },
    ]
  }
}
