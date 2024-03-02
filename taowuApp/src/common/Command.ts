interface Command {
    ADD_FRIEND: number;
    LOGIN: number;
    LOGOUT: number;
    HEART_BEAT: number;
    MessageCommand: {
      PRIVATE_CHAT: number;
      PRIVATE_CHAT_ACK: number;
      PRIVATE_CHAT_RECEIVE_ACK: number;
      PRIVATE_CHAT_READ: number;
      PRIVATE_CHAT_READ_SYNC: number;
      PRIVATE_CHAT_READ_TELL_SEND: number;
    };
    GroupCommand: {
      GROUP_CHAT: number;
      GROUP_CHAT_ACK: number;
    };
  }
  
  const command: Command = {
    ADD_FRIEND: 1001,
    LOGIN: 1002,
    LOGOUT: 1003,
    HEART_BEAT: 1004,
    MessageCommand: {
      PRIVATE_CHAT: 2001,
      PRIVATE_CHAT_ACK: 2002,
      PRIVATE_CHAT_RECEIVE_ACK: 2003,
      PRIVATE_CHAT_READ: 2004,
      PRIVATE_CHAT_READ_SYNC: 2005,
      PRIVATE_CHAT_READ_TELL_SEND: 2006,
    },
    GroupCommand: {
      GROUP_CHAT: 3001,
      GROUP_CHAT_ACK: 3002,
    },
  };
  
  export default command;
  