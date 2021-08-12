import React, { useState, useEffect } from 'react';
import { useParams, useHistory } from 'react-router-dom';
import { Swiper, SwiperSlide } from 'swiper/react';
import SwiperCore, { Pagination } from 'swiper/core';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { OOTDDetailApi } from '../../../../../API/OOTDAPI/OOTDDetailApi';
import 'swiper/swiper.min.css';
import 'swiper/components/pagination/pagination.min.css';

const OOTDContentApi = () => {
  const history = useHistory();
  const myInfo = JSON.parse(localStorage.getItem('userInfo'));
  const { id, nickname } = useParams();
  const [user, setUser] = useState({
    nickname,
    userphoto: '사진',
  });

  const [detail, setDetail] = useState({
    id,
    content: '',
    top: '',
    bottom: '',
    shoes: '',
    accessory: '',
    picture: [],
    views: '',
    hashtag: [],
    registeredTime: '',
    likeMembers: [],
  });

  const [myLike, setMyLike] = useState(false);

  useEffect(async () => {
    const response = await OOTDDetailApi(id);
    if (response.status === 200) {
      setDetail({ ...detail, ...response.detail });
    }
  }, []);

  const ootdUpdateHandler = () => {
    const info = {
      id: detail.id,
      top: detail.top,
      bottom: detail.bottom,
      shoes: detail.shoes,
      accessory: detail.accessory,
      content: detail.content,
      picture: detail.picture,
      hashTag: detail.hashtag,
    };

    history.push({
      pathname: `/OOTDUpdate/${detail.id}/${user.nickname}`,
      state: { info },
    });
  };

  const ootdDeleteHandler = () => {
    const response = OOTDDetailApi(id);
    if (response.status === 200) {
      history.push('/OOTD');
      console.log('삭제 완료');
    }
  };

  const likeHandler = () => {
    setMyLike(!myLike);
  };

  return (
    <article className="detail-content">
      <section className="detail-info">
        <div className="detail-info-photo">{user.userphoto}</div>
        <div className="detail-info-content">
          <p>{user.nickname}</p>
          <p>
            {detail.registeredTime} views:{detail.views}
          </p>
          {myInfo.nickname === user.nickname ? (
            <button type="button" onClick={ootdUpdateHandler}>
              수정
            </button>
          ) : null}
          {myInfo.nickname === user.nickname ? (
            <button type="button" onClick={ootdDeleteHandler}>
              삭제
            </button>
          ) : null}
        </div>
      </section>
      <section className="detail-ootd-photo">
        <Swiper pagination className="detail-swiper-container">
          {detail.picture.length === 0 &&
            detail.picture.map(picture => {
              return (
                <SwiperSlide className="detail-swiper-slide" key={picture}>
                  <img src={picture} alt="OotdPhoto" />
                </SwiperSlide>
              );
            })}
        </Swiper>
      </section>
      <section className="detail-ootd">
        <div className="detail-ootd-like">
          {myLike ? (
            <AiFillHeart
              className="detail-ootd-heart"
              size="20"
              onClick={likeHandler}
            />
          ) : (
            <AiOutlineHeart
              className="detail-ootd-heart"
              size="20"
              onClick={likeHandler}
            />
          )}
          {detail.likeMembers.length}
        </div>
        <div className="detail-ootd-content">
          <p>{detail.content}</p>
        </div>
        <div className="detail-ootd-clothes">
          <div className="detail-ootd-clothes1">
            <p>Top</p>
            <p>Bottom</p>
            <p>Shoes</p>
            <p>Acc</p>
          </div>
          <div className="detail-ootd-clothes2">
            <p>{detail.top}</p>
            <p>{detail.bottom}</p>
            <p>{detail.shoes}</p>
            <p>{detail.accessory}</p>
          </div>
        </div>
        <div className="detail-ootd-hashtag">
          <p>{detail.hashtag.map(hashtag => `#${hashtag} `)}</p>
        </div>
      </section>
    </article>
  );
};

export default OOTDContentApi;
