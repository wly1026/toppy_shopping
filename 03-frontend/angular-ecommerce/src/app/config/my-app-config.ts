export default {

    oidc: {
        clientId: '0oav1k5a0mKZFYQWb5d6',
        issuer: 'https://dev-27789136.okta.com/oauth2/default',
        redirectUri: 'http://localhost:4200/login/callback',
        scopes: ['openid', 'profile', 'email'] 
        // provide access to info about a user
        // openid: required for authentication requests; 
        // profile: user's first name, last name, phone

    }

}
