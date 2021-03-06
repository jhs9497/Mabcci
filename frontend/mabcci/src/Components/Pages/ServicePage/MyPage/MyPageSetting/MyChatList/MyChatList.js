/* eslint-disable */
import '../../MyPageFollow/Follow.css';
import React from 'react';
import 기본프로필 from '../../../../../../Asset/Images/기본프로필.jpg';

const MyChatList = props => {

  const closeChatList = () => {
    props.setChatBox(false)
  }

  return (
    <>
      {props.chatBox === true ? (
        <div className="mypage-modal-container" />
      ) : null}
      {props.chatBox === true ? (
        <div className="mypage-modal-box">
          <div className="mypage-modal-box-header">
            <h5>Chat List</h5>
            <button
              type="submit"
              className="mypage-modal-box-btn"
              onClick={closeChatList}
            >
              X
            </button>
          </div>
          <div className="mypage-modal-box-content">
            <img src={기본프로필} alt="하이" />
            <p>유저네임</p>
            <button type="submit">입장</button>
          </div>
        </div>
      ) : null}
    </>
  );
};

export default MyChatList;
