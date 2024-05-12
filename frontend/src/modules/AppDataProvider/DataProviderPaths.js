export const Models = {
    artist: 'artist',
    event: 'event',
    location: 'location',
    ticket: 'ticket',
    user: 'user',
    userspecifics: 'userspecifics',
    auth: 'auth'
}

export const Operations = {
    import: 'import',
    get: 'get',
    order: 'order',
    archive: 'archive',
    login: 'login',
    register: 'register',
    getTickets: 'get_tickets',
    getArtists: 'get_artists',
    update: 'update'
}

export const Privileges = {
    core: 'core',
    admin: 'admin',
    none: 'none'
}

export function constructApiPath(model: string, operation: string, privilege: string) {
    if (!isModelAvailable(model) || !isOperationAvailable(operation) || !isPrivilegeAvailable(privilege)) {
        throw new Error('Invalid model, operation or privilege');
    }
    const privilegeRequired = privilege !== Privileges.none ? `/${privilege}` : '';
    return `${privilegeRequired}/${model}/${operation}`;
}

function isModelAvailable(model: string) {
    return Object.values(Models).includes(model);
}

function isOperationAvailable(operation: string) {
    return Object.values(Operations).includes(operation);
}

function isPrivilegeAvailable(privilege: string) {
    return Object.values(Privileges).includes(privilege);
}