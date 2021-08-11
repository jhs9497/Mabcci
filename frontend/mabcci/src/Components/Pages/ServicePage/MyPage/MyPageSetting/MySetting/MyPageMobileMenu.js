/* eslint-disable */

import React from 'react';
import './MySetting.css';
import { IoArrowBackCircle } from 'react-icons/io5';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { Logout } from '../../../../../../Redux/Actions/LoginAction';
import MyCategoryMobile from './MyCategoryMobile';
import MyInfoMobile from './MyInfoMobile';
import MyProfileMobile from './MyProfileMobile';
import { useState } from 'react';

const MyPageMobileMenu = props => {
  const dispatch = useDispatch();
  const history = useHistory();

  const userInfo = JSON.parse(localStorage.getItem('userInfo'));

  const [myPageUpdate, setMyPageUpdate] = useState('none')

  const goBack = () => {
    props.setMobileMenu(false)
  };

  const LogOut = () => {
    localStorage.clear();
    dispatch(Logout());
    history.push('/intro');
  };

  const goMyPageUpdate = e => {
    props.setMyPageUpdate(e.target.name);
    props.setMobileMenu(false)
  }

  const goToMobileProposal = () => {
    props.setProposalBox(true)
    props.setMobileMenu(false);
  };

  return (
    <>
      <MyCategoryMobile
        myPageUpdate={props.myPageUpdate}
        setMyPageUpdate={props.setMyPageUpdate}
        setMobileMenu={props.setMobileMenu}
        myInfo={props.myInfo}
        setMyInfo={props.setMyInfo}
      />
      <MyInfoMobile
        myPageUpdate={props.myPageUpdate}
        setMyPageUpdate={props.setMyPageUpdate}
        setMobileMenu={props.setMobileMenu}
        myInfo={props.myInfo}
        setMyInfo={props.setMyInfo}
      />
      <MyProfileMobile
        myPageUpdate={props.myPageUpdate}
        setMyPageUpdate={props.setMyPageUpdate}
        setMobileMenu={props.setMobileMenu}
        myInfo={props.myInfo}
        setMyInfo={props.setMyInfo}
      />
      {props.mobileMenu === true ? (
        <div className="mypage-moblie-container" />
      ) : null}
      {props.mobileMenu === true ? (
        <div className="mypage-mobile-menu">
          <div className="mypage-mobile-menu-header">
            <button
              type="submit"
              className="mypage-mobile-menu-btn"
              onClick={goBack}
            >
              <IoArrowBackCircle />
            </button>
            <h1>설정</h1>
          </div>
          <div className="mypage-mobile-menu-content">
            <h3>나의 계정</h3>
            <div>
              <button type="submit" name="info" onClick={goMyPageUpdate}>
                내 정보 변경
              </button>
            </div>
            <div>
              <button type="submit" name="category" onClick={goMyPageUpdate}>
                카테고리 변경
              </button>
            </div>
            <div>
              <button type="submit" name="profile" onClick={goMyPageUpdate}>
                프로필 변경
              </button>
            </div>
          </div>
          <div className="mypage-mobile-menu-content">
            <h3>서비스</h3>
            <div>
              <button type="submit">대화목록</button>
            </div>
            <div>
              <button type="submit" onClick={goToMobileProposal}>
                최종제안서
              </button>
            </div>
          </div>
          <button
            type="submit"
            className="mobile-menu-logout-btn"
            onClick={LogOut}
          >
            로그아웃
          </button>
        </div>
      ) : null}
    </>
  );
};

export default MyPageMobileMenu;
