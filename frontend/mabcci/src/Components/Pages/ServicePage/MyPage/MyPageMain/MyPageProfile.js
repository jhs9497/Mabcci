/* eslint-disable */

import React from 'react';
import './MyPage.css';
import { useState } from 'react';
import FollowApi from '../../../../../API/MypageAPI/FollowApi';
import UnFollowApi from '../../../../../API/MypageAPI/UnFollowApi';
import {AiOutlineSetting} from "react-icons/ai"
import {CgFileDocument} from "react-icons/cg"
import {AiOutlineMessage} from 'react-icons/ai'
import 기본프로필 from '../../../../../Asset/Images/기본프로필.jpg'
import getUserInfo from '../../../../Common/getUserInfo';
import { BsArrowRepeat } from 'react-icons/bs'

const MyPageProfile = props => {
  const [profile, setProfile] = useState(false)

  const userInfo = getUserInfo();

  const clickProfile = () => {setProfile(!profile)}
  
  const openSetting = () => {
    props.setMyPageUpdate('setting');
    props.setMobileMenu(true);
  };

   const clickFollow = (e) => {
     props.setFollowBox(e.target.name)
   }

   const clickChatList = () => {
     props.setChatBox(true)
   }

   const clickProposalList = () => {
     props.setProposalBox(true)
   }

   const follow = async () => {
     const followMembers = {
       following: props.myInfo.nickname,
       follower: userInfo.nickname,
     };
     const res = await FollowApi(followMembers);
     console.log(res);
   }

   const unFollow = async () => {
     const followMembers = {
       following: props.myInfo.nickname,
       follower: userInfo.nickname,
     };
     const res = await UnFollowApi(followMembers);
     console.log(res);
   };

  return (
    <>
      <div className="mypage-profile-box">
        <div
          onClick={clickProfile}
          className={
            profile === true
              ? 'mypage-profile-inner mypage-profile-inner-active'
              : 'mypage-profile-inner'
          }
        >
          <div className="mypage-profile-bodyinfo">
            {props.myInfo.bodyType !== null ? (
              <img
                src={props.myInfo.gender + '_' + props.myInfo.bodyType}
                alt="No image"
              ></img>
            ) : (
              <div className="mypage-profile-bodysize-noimage">No Type</div>
            )}
            <div className="mypage-profile-bodysize">
              {props.myInfo.height !== 0 ? (
                <p>{props.myInfo.height}cm</p>
              ) : (
                <p className="mypage-profile-bodysize-secret">secret</p>
              )}
              {props.myInfo.weight !== 0 ? (
                <p>{props.myInfo.height}cm</p>
              ) : (
                <p className="mypage-profile-bodysize-secret">secret</p>
              )}
              {props.myInfo.footSize !== 0 ? (
                <p>{props.myInfo.footSize}mm</p>
              ) : (
                <p className="mypage-profile-bodysize-secret">secret</p>
              )}
            </div>
          </div>

          {props.myInfo.picture !== null ? (
            <img src={props.myInfo.picture} alt="No image"></img>
          ) : (
            <img src={기본프로필} alt="" onClick={clickProfile} />
          )}
          <BsArrowRepeat className="mypage-picture-btn" />
        </div>

        <div className="mypage-info-box">
          <div id="mypage-web-nickname">
            <h3>{props.myInfo.nickname}</h3>

            {userInfo.nickname === props.myInfo.nickname ? (
              <div>
                <button type="submit">
                  <CgFileDocument onClick={clickProposalList} />
                </button>
                <button type="submit">
                  <AiOutlineMessage onClick={clickChatList} />
                </button>
                <button type="submit" onClick={openSetting}>
                  <AiOutlineSetting />
                </button>
              </div>
            ) : (
              <div>
                <button type="submit" onClick={follow} id="mypage-follow-btn">
                  팔로우
                </button>
                <button type="submit" onClick={unFollow} id="mypage-follow-btn">
                  팔로우취소
                </button>
                {props.myInfo.role === 'MABCCI' &&
                userInfo.nickname !== props.myInfo.nickname ? (
                  <button type="submit" id="mypage-mabcci-styling-btn">
                    Styling 신청
                  </button>
                ) : null}
              </div>
            )}
          </div>
          <div id="mypage-mobile-nickname">
            <div>
              <h3>{props.myInfo.nickname}</h3>
              {userInfo.nickname === props.myInfo.nickname ? null : (
                <div>
                  <button type="submit" onClick={follow} id="mypage-follow-btn">
                    팔로우
                  </button>
                  <button
                    type="submit"
                    onClick={unFollow}
                    id="mypage-follow-btn"
                  >
                    팔로우취소
                  </button>
                  {props.myInfo.role === 'MABCCI' &&
                  userInfo.nickname !== props.myInfo.nickname ? (
                    <button type="submit" id="mypage-mabcci-styling-btn">
                      Styling 신청
                    </button>
                  ) : null}
                </div>
              )}
            </div>
          </div>
          <div>
            <button type="submit" onClick={clickFollow} name="팔로워">
              팔로워 {props.myInfo.follower}명
            </button>
            <button type="submit" onClick={clickFollow} name="팔로잉">
              팔로잉 {props.myInfo.following}명
            </button>
          </div>
          <div id="mypage-mobile-category">
            {props.myInfo.categories.map((category, idx) => (
              <h5 key={idx}>#{category}</h5>
            ))}
          </div>
        </div>
        <div className="mypage-blank" />
      </div>
      <div className="mypage-introduce-box">
        <p>Introduce</p>

        {props.myInfo.description == 'null' ||
        props.myInfo.description == '' ||
        props.myInfo.description == null ? (
          <div className="mypage-introduce-box-null">No information❕</div>
        ) : (
          <div>{props.myInfo.description}</div>
        )}
      </div>
    </>
  );
};

export default MyPageProfile;
